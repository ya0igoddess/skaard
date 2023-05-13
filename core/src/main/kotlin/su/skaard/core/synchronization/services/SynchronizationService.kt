package su.skaard.core.synchronization.services

import dev.kord.core.Kord
import dev.kord.core.entity.Guild
import dev.kord.core.entity.Member
import dev.kord.core.entity.User
import dev.kord.core.entity.channel.GuildChannel
import dev.kord.core.event.channel.VoiceChannelCreateEvent
import dev.kord.core.event.guild.MemberJoinEvent
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import su.skaard.core.entities.discord.Channel
import su.skaard.core.entities.discord.DiscordUser
import su.skaard.core.entities.discord.GuildMember
import su.skaard.core.repositories.discord.ChannelRepository
import su.skaard.core.repositories.discord.DiscordUserRepository
import su.skaard.core.repositories.discord.GuildMemberRepository
import su.skaard.core.repositories.discord.GuildsRepository
import su.skaard.core.utils.IntegrationPersistenceException
import su.skaard.core.utils.getLogger
import su.skaard.core.utils.lvalue

/**
 * Synchronizes Discord Api guild, channel and member data with local DB.
 */
@Component
class SynchronizationService(
    private val channelRepository: ChannelRepository,
    private val discordUserRepository: DiscordUserRepository,
    private val guildMemberRepository: GuildMemberRepository,
    private val guildsRepository: GuildsRepository,
    private val guildSyncService: IDiscordSyncRepoService<su.skaard.core.entities.discord.Guild, Guild>,
    private val channelSyncService: IDiscordSyncRepoService<Channel, dev.kord.core.entity.channel.Channel>,
) : ISynchronizationService {
    private val logger = getLogger(SynchronizationService::class.java)

    override fun synchronizeData(kord: Kord) {
        logger.info("Kord synchronization started")
        runBlocking { kord.guilds.collect(::syncGuild) }
        logger.info("Kord synchronization finished")
    }

    override suspend fun handleVoiceChannelCreateEvent(voiceChannelCreateEvent: VoiceChannelCreateEvent) {
        logger.debug("Handling channel creation {}", voiceChannelCreateEvent)
        val discordChannel = voiceChannelCreateEvent.channel
        val discordGuild = discordChannel.guild
        val guild =
            guildsRepository.findById(discordGuild.id.lvalue) ?: throw IntegrationPersistenceException()
        val channel = Channel(discordChannel.id.lvalue, 0L)
        channelRepository.save(channel)
    }

    override suspend fun handleMemberJoinEvent(memberJoinEvent: MemberJoinEvent) {
        logger.debug("Handling member joining {}", memberJoinEvent)
        val discordUser = runBlocking { memberJoinEvent.member.asUser() }
        val user = syncUser(discordUser)
        val guild = guildsRepository.findById(memberJoinEvent.guild.id.lvalue)
            ?: throw IntegrationPersistenceException()
        val member = GuildMember(
            id = user.id,
            discordUserId = user.id,
            guildId = guild.id
        )
        guildMemberRepository.save(member)
    }

    override suspend fun syncGuild(discordGuild: Guild) {
        guildSyncService.findOrCreateFromExt(discordGuild)
        discordGuild.channels.collect(::syncChannel)
        //runBlocking { discordGuild.members.toList() }.forEach { syncGuildMember(it, guild) }
    }

    override suspend fun syncChannel(discordChannel: GuildChannel) {
         channelSyncService.findOrCreateFromExt(discordChannel)
    }

    override suspend fun syncUser(discordUser: User): DiscordUser {
        val user = discordUserRepository.findById(discordUser.id.lvalue)
            ?: DiscordUser(
                id = discordUser.id.lvalue,
                name = discordUser.username
            )
        return discordUserRepository.save(user)
    }

    override suspend fun syncGuildMember(discordMember: Member, guild: su.skaard.core.entities.discord.Guild) {
        val user = syncUser(runBlocking { discordMember.asUser() })
        val member = guildMemberRepository.getByGuildIdAndDiscordUserId(guild.id, user.id)
            ?: GuildMember(
                id = discordMember.id.lvalue,
                discordUserId = user.id,
                guildId = guild.id
            )
        guildMemberRepository.save(member)
    }
}
