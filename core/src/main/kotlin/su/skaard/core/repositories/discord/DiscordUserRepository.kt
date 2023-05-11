package su.skaard.core.repositories.discord

import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import su.skaard.core.entities.discord.DiscordUser
import java.util.*

@Repository
interface DiscordUserRepository : CoroutineCrudRepository<DiscordUser, ULong> {
    override suspend fun findById(id: ULong): DiscordUser?

    override suspend fun <S : DiscordUser> save(entity: S): S
}
