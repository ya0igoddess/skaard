package su.skaard.integration.discord.beans

import dev.kord.core.Kord
import dev.kord.core.event.channel.VoiceChannelCreateEvent
import dev.kord.core.event.guild.MemberJoinEvent
import dev.kord.core.event.interaction.ChatInputCommandInteractionCreateEvent
import dev.kord.core.event.user.VoiceStateUpdateEvent
import dev.kord.core.on
import dev.kord.gateway.Intent
import dev.kord.gateway.PrivilegedIntent
import kotlinx.coroutines.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import su.skaard.utils.getLogger
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

@Component
class KordSingleton @Autowired constructor(
    val connectionPeriodRegistryService: ConnectionPeriodRegistryService,
    val synchronisingBean: SynchronisingBean,
    val kordCommandRegistry: KordCommandRegistry,
    val kordCommandDispatcher: KordCommandDispatcher,
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
        kord.on<VoiceStateUpdateEvent> { connectionPeriodRegistryService.handleVoiceChange(this) }
        kord.on<VoiceChannelCreateEvent> { synchronisingBean.handleVoiceChannelCreateEvent(this) }
        kord.on<MemberJoinEvent> { synchronisingBean.handleMemberJoinEvent(this) }
        kord.on<ChatInputCommandInteractionCreateEvent> { kordCommandDispatcher.handleCommandEvent(this) }
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