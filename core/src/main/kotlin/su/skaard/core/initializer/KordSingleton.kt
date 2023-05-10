package su.skaard.core.initializer

import dev.kord.core.Kord
import dev.kord.gateway.Intent
import dev.kord.gateway.PrivilegedIntent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Component
import su.skaard.core.utils.getLogger
import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty

@Component
@ConditionalOnProperty("skaard.core.bot.start", matchIfMissing = false)
class KordSingleton(
    private val preInits: List<IKordPreInitializer>,
    @Value("\${skaard.core.bot.token}")
    private val token: String
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
