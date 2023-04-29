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
import org.springframework.beans.factory.getBeansOfType
import org.springframework.context.ApplicationContext
import su.skaard.core.commands.services.IKordCommandRegistry
import su.skaard.core.initializer.IKordPreInitializer
import su.skaard.core.synchronization.services.ISynchronizationService

@Component
class KordSingleton @Autowired constructor(
    val context: ApplicationContext,
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

        context.getBeansOfType<IKordPreInitializer>().values.forEach { it.preInitialize(kord) }

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
