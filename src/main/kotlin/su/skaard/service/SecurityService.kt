package su.skaard.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import su.skaard.model.discord.Channel
import su.skaard.model.discord.DiscordUser
import su.skaard.model.discord.Guild
import su.skaard.repositories.discord.GuildMemberRepository

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
