package su.skaard.integration.discord.beans

import dev.kord.core.Kord
import dev.kord.gateway.Intent
import dev.kord.gateway.PrivilegedIntent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import su.skaard.core.utils.getLogger
import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import su.skaard.core.initializer.IKordPreInitializer

@Component
class KordSingleton(
    private val preInits: List<IKordPreInitializer>,
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

        preInits.forEach { it.preInitialize(kord) }

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
