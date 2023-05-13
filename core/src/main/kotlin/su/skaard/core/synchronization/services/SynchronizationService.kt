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
import su.skaard.core.services.repo.IGuildMemberService
import su.skaard.core.utils.IntegrationPersistenceException
import su.skaard.core.utils.getLogger
import su.skaard.core.utils.lvalue

/**
 * Synchronizes Discord Api guild, channel and member data with local DB.
 */
@Component
class SynchronizationService(
    private val guildSyncService: IDiscordSyncRepoService<su.skaard.core.entities.discord.Guild, Guild>,
    private val channelSyncService: IDiscordSyncRepoService<Channel, dev.kord.core.entity.channel.Channel>,
    private val memberSyncService: ISyncRepoService<GuildMember, Member>,
    private val userSyncService: ISyncRepoService<DiscordUser, User>
) : ISynchronizationService {
    private val logger = getLogger(SynchronizationService::class.java)

    override fun synchronizeData(kord: Kord) {
        logger.info("Kord synchronization started")
        runBlocking { kord.guilds.collect(::syncGuild) }
        logger.info("Kord synchronization finished")
    }

    override suspend fun handleVoiceChannelCreateEvent(voiceChannelCreateEvent: VoiceChannelCreateEvent) {
        logger.debug("Handling channel creation {}", voiceChannelCreateEvent)
        channelSyncService.createFromExternal(voiceChannelCreateEvent.channel)
    }

    override suspend fun handleMemberJoinEvent(memberJoinEvent: MemberJoinEvent) {
        logger.debug("Handling member joining {}", memberJoinEvent)
        memberSyncService.createFromExternal(memberJoinEvent.member)
    }

    override suspend fun syncGuild(discordGuild: Guild) {
        guildSyncService.findOrCreateFromExt(discordGuild)
        discordGuild.channels.collect(::syncChannel)
        discordGuild.members.collect(::syncGuildMember)
    }

    override suspend fun syncChannel(discordChannel: GuildChannel) {
         channelSyncService.findOrCreateFromExt(discordChannel)
    }

    override suspend fun syncUser(discordUser: User) {
        userSyncService.findOrCreateFromExt(discordUser)
    }

    override suspend fun syncGuildMember(discordMember: Member) {
        memberSyncService.findOrCreateFromExt(discordMember)
    }
}
