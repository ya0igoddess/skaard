package su.skaard.integration.discord

import dev.kord.core.Kord
import dev.kord.core.event.user.VoiceStateUpdateEvent
import dev.kord.core.on
import dev.kord.gateway.Intent
import dev.kord.gateway.PrivilegedIntent
import kotlinx.coroutines.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import su.skaard.utils.SettingsStore
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

@Component
class KordSingleton @Autowired constructor(
    settingsStore: SettingsStore,
    connectionPool: KordVoiceConnectionPool
) {

    lateinit var kord: Kord
    private final val connectionPool: KordVoiceConnectionPool
    private final val settingsStore: SettingsStore
    init {
        this.connectionPool = connectionPool
        this.settingsStore = settingsStore
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