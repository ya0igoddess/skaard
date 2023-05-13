package su.skaard.core.services.repo

import dev.kord.core.entity.Member
import su.skaard.core.entities.discord.DiscordUser
import su.skaard.core.entities.discord.DiscordGuild
import su.skaard.core.entities.discord.GuildMember
import su.skaard.core.synchronization.services.ISyncRepoService

interface IGuildMemberService: IRepoService<GuildMember, Long>, ISyncRepoService<GuildMember, Member> {
    suspend fun getByGuildAndUser(guild: DiscordGuild, user: DiscordUser): GuildMember?
}