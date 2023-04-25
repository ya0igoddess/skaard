package su.skaard.integration.discord.beans

import dev.kord.core.Kord
import dev.kord.core.event.Event
import dev.kord.core.on
import dev.kord.gateway.Intent
import dev.kord.gateway.PrivilegedIntent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import su.skaard.core.handlers.DiscordEventHandler
import su.skaard.core.utils.getLogger
import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import su.skaard.core.commands.services.KordCommandRegistry
import su.skaard.core.synchronization.services.SynchronisingBean

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
        initKord()
    }

    @PreDestroy
    fun preDestroy() = runBlocking {
        kord.logout()
    }

    private fun initKord() {
        logger.info("Initialising kord")
        val token = System.getenv("SKAARD_TOKEN")
        kord = runBlocking { Kord(token) }

        synchronisingBean.synchronizeData(kord)
        kordCommandRegistry.registerCommands(kord)
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
