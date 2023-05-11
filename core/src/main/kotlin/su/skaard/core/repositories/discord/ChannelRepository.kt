package su.skaard.core.repositories.discord

import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import su.skaard.core.entities.discord.Channel

@Repository
interface ChannelRepository : CoroutineCrudRepository<Channel, ULong> {
    suspend fun searchById(id: ULong): Channel?
    override suspend fun <S : Channel> save(entity: S): S
}
