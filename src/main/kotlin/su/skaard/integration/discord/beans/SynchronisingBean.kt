package su.skaard.integration.discord.beans

import dev.kord.core.Kord
import dev.kord.core.entity.Guild
import dev.kord.core.entity.Member
import dev.kord.core.entity.User
import dev.kord.core.entity.channel.GuildChannel
import dev.kord.core.event.channel.VoiceChannelCreateEvent
import dev.kord.core.event.guild.MemberJoinEvent

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import su.skaard.model.discord.Channel
import su.skaard.model.discord.DiscordUser
import su.skaard.model.discord.GuildMember
import su.skaard.repositories.discord.ChannelRepository
import su.skaard.repositories.discord.DiscordUserRepository
import su.skaard.repositories.discord.GuildMemberRepository
import su.skaard.repositories.discord.GuildsRepository
import su.skaard.utils.getLogger

/**
 * Synchronizes Discord Api guild, channel and member data with local DB.
 */
@Component
class SynchronisingBean @Autowired constructor (
    private val channelRepository: ChannelRepository,
    private val discordUserRepository: DiscordUserRepository,
    private val guildMemberRepository: GuildMemberRepository,
    private val guildsRepository: GuildsRepository
) {
    private val logger = getLogger(SynchronisingBean::class.java)

    suspend fun synchronizeData(kord: Kord) {
        logger.info("Kord synchronization started")
        kord.guilds.collect { syncGuild(it) }
        logger.info("Kord synchronization finished")
    }

    suspend fun handleVoiceChannelCreateEvent(voiceChannelCreateEvent: VoiceChannelCreateEvent) {
        logger.debug("Handling channel creation $voiceChannelCreateEvent")
        val discordChannel = voiceChannelCreateEvent.channel
        val discordGuild = discordChannel.guild
        val guild = guildsRepository.findById(discordGuild.id.value.toLong())
            .orElseThrow()
        val channel = Channel(discordChannel.id.value, guild)
        channelRepository.save(channel)
    }

    suspend fun handleMemberJoinEvent(memberJoinEvent: MemberJoinEvent) {
        logger.debug("Handling member joining $memberJoinEvent")
        val discordUser = memberJoinEvent.member.asUser()
        val user = syncUser(discordUser)!!
        val guild = guildsRepository.findById(memberJoinEvent.guild.id.value.toLong()).orElseThrow()
        val member = GuildMember(
            id = user.id,
            discordUser = user,
            guild = guild
        )
        guildMemberRepository.save(member)
    }

    suspend fun syncGuild(discordGuild: Guild) {
        val guildOpt = guildsRepository.findById(discordGuild.id.value.toLong())
        val guild = guildOpt.orElse(su.skaard.model.discord.Guild(
            id = discordGuild.id.value
        ))
        guildsRepository.save(guild)
        discordGuild.channels.collect { syncChannel(it, guild) }
        discordGuild.members.collect { syncGuildMember(it, guild) }
    }

    suspend fun syncChannel(discordChannel: GuildChannel, guild: su.skaard.model.discord.Guild) {
        val channelOpt = channelRepository.findById(discordChannel.id.value.toLong())
        val channel = channelOpt.orElse(Channel(
            id = discordChannel.id.value,
            guild = guild
        ))
        channelRepository.save(channel)
    }

    suspend fun syncUser(discordUser: User): DiscordUser? {
        val userOpt = discordUserRepository.findById(discordUser.id.value.toLong())
        val user = userOpt.orElse(DiscordUser(
            id = discordUser.id.value,
            name = discordUser.username
        ))
        return discordUserRepository.save(user)
    }

    suspend fun syncGuildMember(discordMember: Member, guild: su.skaard.model.discord.Guild) {
        val user = syncUser(discordMember.asUser())!!
        val memberOpt = guildMemberRepository.findByGuildAndDiscordUser(guild, user)
        val member = memberOpt.orElse(GuildMember(
            id = discordMember.id.value,
            discordUser = user,
            guild = guild
        ))
        guildMemberRepository.save(member)
    }

}