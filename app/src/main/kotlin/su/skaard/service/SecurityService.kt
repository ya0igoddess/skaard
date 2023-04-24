package su.skaard.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import su.skaard.core.entities.discord.Channel
import su.skaard.core.entities.discord.DiscordUser
import su.skaard.core.entities.discord.Guild
import su.skaard.core.repositories.discord.GuildMemberRepository

@Service
class SecurityService @Autowired constructor(
    val guildMemberRepository: GuildMemberRepository
) {
    fun isUserMemberOfGuild(user: DiscordUser, guild: Guild): Boolean {
        return guildMemberRepository.getByGuildAndDiscordUser(guild, user) != null
    }

    fun isUserMemberOfChannel(user: DiscordUser, channel: Channel): Boolean {
        return isUserMemberOfGuild(user, channel.guild)
    }
}
