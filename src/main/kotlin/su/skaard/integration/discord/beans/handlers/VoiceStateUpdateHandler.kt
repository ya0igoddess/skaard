package su.skaard.integration.discord.beans.handlers

import dev.kord.core.event.Event
import dev.kord.core.event.user.VoiceStateUpdateEvent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import su.skaard.integration.discord.beans.ConnectionPeriodRegistryService

@Component
class VoiceStateUpdateHandler @Autowired constructor(
    private val connectionPeriodRegistryService: ConnectionPeriodRegistryService
) : DiscordEventHandler {
    override suspend fun handle(event: Event) {
        if (event !is VoiceStateUpdateEvent) return
        connectionPeriodRegistryService.handleVoiceChange(event)
    }

}