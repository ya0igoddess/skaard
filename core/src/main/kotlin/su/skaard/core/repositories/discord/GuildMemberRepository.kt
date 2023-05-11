package su.skaard.core.repositories.discord

import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import su.skaard.core.entities.discord.DiscordUser
import su.skaard.core.entities.discord.Guild
import su.skaard.core.entities.discord.GuildMember

@Repository
interface GuildMemberRepository : CoroutineCrudRepository<GuildMember, ULong> {
    override suspend fun findById(id: ULong): GuildMember?
    suspend fun getByGuildAndDiscordUser(guild: Guild, discordUser: DiscordUser): GuildMember?

    suspend fun getByGuildIdAndDiscordUserId(guild: Long, discordUserId: Long): GuildMember?
    override suspend fun <S : GuildMember> save(entity: S): S
}
