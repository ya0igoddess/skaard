package su.skaard.core.synchronization.services

import dev.kord.core.Kord
import dev.kord.core.entity.Guild
import dev.kord.core.entity.Member
import dev.kord.core.entity.User
import dev.kord.core.entity.channel.GuildChannel
import dev.kord.core.event.channel.VoiceChannelCreateEvent
import dev.kord.core.event.guild.MemberJoinEvent
import org.springframework.transaction.annotation.Transactional
import su.skaard.core.entities.discord.DiscordUser

interface ISynchronizationService {
    @Transactional
    fun synchronizeData(kord: Kord)
    fun handleVoiceChannelCreateEvent(voiceChannelCreateEvent: VoiceChannelCreateEvent)
    fun handleMemberJoinEvent(memberJoinEvent: MemberJoinEvent)
    fun syncGuild(discordGuild: Guild)
    fun syncChannel(discordChannel: GuildChannel, guild: su.skaard.core.entities.discord.Guild)
    fun syncUser(discordUser: User): DiscordUser
    fun syncGuildMember(discordMember: Member, guild: su.skaard.core.entities.discord.Guild)
}