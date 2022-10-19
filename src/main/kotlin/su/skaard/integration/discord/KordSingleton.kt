package su.skaard.integration.discord

import dev.kord.core.Kord
import dev.kord.core.event.channel.VoiceChannelUpdateEvent
import dev.kord.core.event.message.MessageCreateEvent
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
class KordSingleton @Autowired constructor(connectionPool: KordVoiceConnectionPool) {

    lateinit var kord: Kord
    lateinit var korLoginJob: Job
    val connectionPool: KordVoiceConnectionPool
    init {
        this.connectionPool = connectionPool
    }

    @PostConstruct
    fun postInit() {
        runBlocking { initKord() }
    }

    @PreDestroy
    fun preDestroy() {
        korLoginJob.cancel()
    }

    private suspend fun initKord() {
        val token = ""
        kord = Kord(token)
        kord.on<VoiceStateUpdateEvent> { connectionPool.handleVoiceChange(this) }
        korLoginJob = CoroutineScope(kord.coroutineContext).launch {
            kord.login {
                @OptIn(PrivilegedIntent::class)
                intents += Intent.MessageContent
            }
        }
    }
}