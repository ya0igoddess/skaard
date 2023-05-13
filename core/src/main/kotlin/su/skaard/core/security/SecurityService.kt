package su.skaard.core.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import su.skaard.core.entities.discord.Channel
import su.skaard.core.entities.discord.DiscordUser
import su.skaard.core.entities.discord.Guild
import su.skaard.core.repositories.discord.GuildMemberRepository

@Service
class SecurityService(
    val guildMemberRepository: GuildMemberRepository
) {
    suspend fun isUserMemberOfGuild(user: DiscordUser, guild: Guild): Boolean {
        return guildMemberRepository.getByGuildIdAndDiscordUserId(guild.id.toLong(), user.id.toLong()) != null
    }

    suspend fun isUserMemberOfChannel(user: DiscordUser, channel: Channel): Boolean {
        return isUserMemberOfGuild(user, Guild(channel.guildId))
    }
}
