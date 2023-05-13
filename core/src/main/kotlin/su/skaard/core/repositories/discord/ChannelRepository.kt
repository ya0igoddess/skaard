package su.skaard.core.repositories.discord

import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import su.skaard.core.entities.discord.Channel

@Repository
interface ChannelRepository : CoroutineCrudRepository<Channel, Long>
