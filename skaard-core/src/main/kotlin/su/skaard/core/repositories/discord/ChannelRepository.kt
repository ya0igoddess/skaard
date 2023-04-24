package su.skaard.core.repositories.discord

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import su.skaard.core.entities.discord.Channel

@Repository
interface ChannelRepository : JpaRepository<Channel, Long> {
    // override fun findById(id: Long): Optional<Channel>
    fun searchById(id: Long): Channel?
    override fun <S : Channel> save(entity: S): S
}
