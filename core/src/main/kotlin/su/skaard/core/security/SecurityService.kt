package su.skaard.core.security

import org.springframework.stereotype.Service
import su.skaard.core.entities.discord.DiscordChannel
import su.skaard.core.entities.discord.DiscordUser
import su.skaard.core.entities.discord.DiscordGuild
import su.skaard.core.repositories.discord.GuildMemberRepository

@Service
class SecurityService(
    val guildMemberRepository: GuildMemberRepository
) {
    suspend fun isUserMemberOfGuild(user: DiscordUser, guild: DiscordGuild): Boolean {
        return guildMemberRepository.getByGuildIdAndDiscordUserId(guild.id.toLong(), user.id.toLong()) != null
    }

    suspend fun isUserMemberOfChannel(user: DiscordUser, channel: DiscordChannel): Boolean {
        return isUserMemberOfGuild(user, DiscordGuild(channel.guildId))
    }
}
