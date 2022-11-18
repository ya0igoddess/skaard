package su.skaard.integration.discord.beans

import dev.kord.core.Kord
import dev.kord.core.event.Event
import dev.kord.core.on
import dev.kord.gateway.Intent
import dev.kord.gateway.PrivilegedIntent
import kotlinx.coroutines.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import su.skaard.integration.discord.beans.handlers.DiscordEventHandler
import su.skaard.utils.getLogger
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

@Component
class KordSingleton @Autowired constructor(
    val synchronisingBean: SynchronisingBean,
    val kordCommandRegistry: KordCommandRegistry,
    val eventHandlers: List<DiscordEventHandler>
) {
    private final val logger = getLogger(KordSingleton::class.java)
    lateinit var kord: Kord

    @PostConstruct
    fun postInit() {
        runBlocking { initKord() }
    }

    @PreDestroy
    fun preDestroy() {
        runBlocking { kord.logout() }
    }

    suspend fun synchronizeData() = synchronisingBean.synchronizeData(kord)
    suspend fun registerCommands() = kordCommandRegistry.registerCommands(kord)

    private suspend fun initKord() {
        val token = System.getenv("SKAARD_TOKEN")
        kord = Kord(token)
        synchronizeData()
        registerCommands()

        kord.on<Event> { eventHandlers.forEach { it.handle(this) } }
        CoroutineScope(kord.coroutineContext).launch {
            kord.login {
                @OptIn(PrivilegedIntent::class)
                intents += Intent.MessageContent
                @OptIn(PrivilegedIntent::class)
                intents += Intent.GuildMembers
                intents += Intent.GuildVoiceStates
                intents += Intent.DirectMessages
            }
        }
    }
}