package su.skaard.core.synchronization.services

import dev.kord.core.Kord
import dev.kord.core.entity.Guild
import dev.kord.core.entity.Member
import dev.kord.core.entity.User
import dev.kord.core.entity.channel.GuildChannel
import dev.kord.core.event.channel.VoiceChannelCreateEvent
import dev.kord.core.event.guild.MemberJoinEvent
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

/**
 * Synchronizes Discord Api guild, channel and member data with local DB.
 */
@Component
class SynchronisingBeanImpl @Autowired constructor(
    private val channelRepository: ChannelRepository,
    private val discordUserRepository: DiscordUserRepository,
    private val guildMemberRepository: GuildMemberRepository,
    private val guildsRepository: GuildsRepository
) : SynchronisingBean {
    private val logger = getLogger(SynchronisingBeanImpl::class.java)

    @Transactional
    override fun synchronizeData(kord: Kord) {
        logger.info("Kord synchronization started")
        runBlocking { kord.guilds.toList() }.forEach(this::syncGuild)
        logger.info("Kord synchronization finished")
    }

    override fun handleVoiceChannelCreateEvent(voiceChannelCreateEvent: VoiceChannelCreateEvent) {
        logger.debug("Handling channel creation {}", voiceChannelCreateEvent)
        val discordChannel = voiceChannelCreateEvent.channel
        val discordGuild = discordChannel.guild
        val guild =
            guildsRepository.searchById(discordGuild.id.value.toLong()) ?: throw IntegrationPersistenceException()
        val channel = Channel(discordChannel.id.value, guild)
        channelRepository.save(channel)
    }

    override fun handleMemberJoinEvent(memberJoinEvent: MemberJoinEvent) {
        logger.debug("Handling member joining {}", memberJoinEvent)
        val discordUser = runBlocking { memberJoinEvent.member.asUser() }
        val user = syncUser(discordUser)
        val guild = guildsRepository.searchById(memberJoinEvent.guild.id.value.toLong())
            ?: throw IntegrationPersistenceException()
        val member = GuildMember(
            id = user.id,
            discordUser = user,
            guild = guild
        )
        guildMemberRepository.save(member)
    }

    override fun syncGuild(discordGuild: Guild) {
        val guild = guildsRepository.searchById(discordGuild.id.value.toLong())
            ?: su.skaard.core.entities.discord.Guild(
                id = discordGuild.id.value
            )
        guildsRepository.save(guild)
        runBlocking { discordGuild.channels.toList() }.forEach { syncChannel(it, guild) }
        runBlocking { discordGuild.members.toList() }.forEach { syncGuildMember(it, guild) }
    }

    override fun syncChannel(discordChannel: GuildChannel, guild: su.skaard.core.entities.discord.Guild) {
        val channel = channelRepository.searchById(discordChannel.id.value.toLong())
            ?: Channel(
                id = discordChannel.id.value,
                guild = guild
            )
        channelRepository.save(channel)
    }

    override fun syncUser(discordUser: User): DiscordUser {
        val user = discordUserRepository.searchById(discordUser.id.value.toLong())
            ?: DiscordUser(
                id = discordUser.id.value,
                name = discordUser.username
            )
        return discordUserRepository.save(user)
    }

    override fun syncGuildMember(discordMember: Member, guild: su.skaard.core.entities.discord.Guild) {
        val user = syncUser(runBlocking { discordMember.asUser() })
        val member = guildMemberRepository.getByGuildAndDiscordUser(guild, user)
            ?: GuildMember(
                id = discordMember.id.value,
                discordUser = user,
                guild = guild
            )
        guildMemberRepository.save(member)
    }
}
