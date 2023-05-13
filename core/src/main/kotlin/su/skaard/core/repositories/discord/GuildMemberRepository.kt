package su.skaard.core.repositories.discord

import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import su.skaard.core.entities.discord.GuildMember

@Repository
interface GuildMemberRepository : CoroutineCrudRepository<GuildMember, Long> {

    suspend fun getByGuildIdAndDiscordUserId(guildId: Long, discordUserId: Long): GuildMember? { throw NotImplementedError() }

}
