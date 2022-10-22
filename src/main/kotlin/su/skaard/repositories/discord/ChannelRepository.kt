package su.skaard.repositories.discord

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import su.skaard.model.discord.Channel
import java.util.*

@Repository
interface ChannelRepository : JpaRepository<Channel, Long> {
    override fun findById(id: Long): Optional<Channel>
    override fun <S : Channel> save(entity: S): S
}