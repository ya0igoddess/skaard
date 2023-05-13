package su.skaard.core.services.repo

import dev.kord.core.entity.Member
import su.skaard.core.entities.discord.DiscordUser
import su.skaard.core.entities.discord.Guild
import su.skaard.core.entities.discord.GuildMember
import su.skaard.core.synchronization.services.ISyncRepoService

interface IGuildMemberService: IRepoService<GuildMember, Long>, ISyncRepoService<GuildMember, Member> {
    suspend fun getByGuildAndUser(guild: Guild, user: DiscordUser): GuildMember?
}