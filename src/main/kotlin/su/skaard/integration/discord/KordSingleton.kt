package su.skaard.integration.discord

import dev.kord.core.Kord
import dev.kord.core.event.user.VoiceStateUpdateEvent
import dev.kord.core.on
import dev.kord.gateway.Intent
import dev.kord.gateway.PrivilegedIntent
import kotlinx.coroutines.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

@Component
class KordSingleton @Autowired constructor(
    connectionPool: KordVoiceConnectionPool
) {

    lateinit var kord: Kord
    private final val connectionPool: KordVoiceConnectionPool
    init {
        this.connectionPool = connectionPool
    }

    @PostConstruct
    fun postInit() {
        runBlocking { initKord() }
    }

    @PreDestroy
    fun preDestroy() {
        runBlocking { kord.logout() }
    }

    private suspend fun initKord() {
        val token = System.getenv("SKAARD_TOKEN")
        kord = Kord(token)
        kord.on<VoiceStateUpdateEvent> { connectionPool.handleVoiceChange(this) }
        CoroutineScope(kord.coroutineContext).launch {
            kord.login {
                @OptIn(PrivilegedIntent::class)
                intents += Intent.MessageContent
            }
        }
    }
}