package su.skaard.integration.discord

import dev.kord.core.Kord
import dev.kord.core.event.channel.VoiceChannelUpdateEvent
import dev.kord.core.event.message.MessageCreateEvent
import dev.kord.core.event.user.VoiceStateUpdateEvent
import dev.kord.core.on
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class KordSingleton @Autowired constructor(connectionPool: KordVoiceConnectionPool) {

    lateinit var kord: Kord
    val connectionPool: KordVoiceConnectionPool
    init {
        this.connectionPool = connectionPool
    }

    @PostConstruct
    suspend fun init() {
        val token = ""
        kord = Kord(token)
        kord.on<VoiceStateUpdateEvent> { connectionPool.handleVoiceChange(this) }
    }
}