package su.skaard.integration.discord.beans.handlers

import dev.kord.core.event.Event
import dev.kord.core.event.channel.VoiceChannelCreateEvent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import su.skaard.integration.discord.beans.SynchronisingBean

@Component
class VoiceChannelCreateHandler @Autowired constructor(
    private val synchronisingBean: SynchronisingBean
) : DiscordEventHandler {
    override suspend fun handle(event: Event) {
        if (event !is VoiceChannelCreateEvent) return
        synchronisingBean.handleVoiceChannelCreateEvent(event)
    }
}
