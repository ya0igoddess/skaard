package su.skaard.integration.discord

import dev.kord.core.Kord
import dev.kord.core.event.channel.VoiceChannelCreateEvent
import dev.kord.core.event.guild.MemberJoinEvent
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
    connectionPool: KordVoiceConnectionPool,
    synchronisingBean: SynchronisingBean
) {
    lateinit var kord: Kord
    private final val connectionPool: KordVoiceConnectionPool
    private final val synchronisingBean: SynchronisingBean
    init {
        this.connectionPool = connectionPool
        this.synchronisingBean = synchronisingBean
    }

    @PostConstruct
    fun postInit() {
        runBlocking { initKord() }
    }

    @PreDestroy
    fun preDestroy() {
        runBlocking { kord.logout() }
    }

    suspend fun synchronizeData() = synchronisingBean.synchronizeData(kord)

    private suspend fun initKord() {
        val token = System.getenv("SKAARD_TOKEN")
        kord = Kord(token)
        synchronizeData()
        kord.on<VoiceStateUpdateEvent> { connectionPool.handleVoiceChange(this) }
        kord.on<VoiceChannelCreateEvent> { synchronisingBean.handleVoiceChannelCreateEvent(this) }
        kord.on<MemberJoinEvent> { synchronisingBean.handleMemberJoinEvent(this) }
        CoroutineScope(kord.coroutineContext).launch {
            kord.login {
                @OptIn(PrivilegedIntent::class)
                intents += Intent.MessageContent
                @OptIn(PrivilegedIntent::class)
                intents += Intent.GuildMembers
            }
        }
    }
}