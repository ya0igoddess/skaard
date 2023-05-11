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
    suspend fun handleVoiceChannelCreateEvent(voiceChannelCreateEvent: VoiceChannelCreateEvent)
    suspend fun handleMemberJoinEvent(memberJoinEvent: MemberJoinEvent)
    suspend fun syncGuild(discordGuild: Guild)
    suspend fun syncChannel(discordChannel: GuildChannel, guild: su.skaard.core.entities.discord.Guild)
    suspend fun syncUser(discordUser: User): DiscordUser
    suspend fun syncGuildMember(discordMember: Member, guild: su.skaard.core.entities.discord.Guild)
}
