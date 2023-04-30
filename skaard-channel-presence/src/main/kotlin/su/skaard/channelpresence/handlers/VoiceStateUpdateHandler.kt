package su.skaard.channelpresence.handlers

import dev.kord.core.event.Event
import dev.kord.core.event.user.VoiceStateUpdateEvent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import su.skaard.core.handlers.DiscordEventHandler
import su.skaard.channelpresence.services.ConnectionPeriodRegistryService

@Component
class VoiceStateUpdateHandler @Autowired constructor(
    private val connectionPeriodRegistryService: ConnectionPeriodRegistryService
) : DiscordEventHandler {

    override suspend fun handle(event: Event) {
        if (event !is VoiceStateUpdateEvent) return
        if (!isChannelSwitch(event)) return // interested in channel switch only
        event.old?.let { connectionPeriodRegistryService.closeConnection(it.sessionId) }
        if (event.state.channelId != null) {
            event.state.let {
                connectionPeriodRegistryService.openConnection(it.sessionId, it.channelId!!, it.userId)
            }
        }
    }
    private fun isChannelSwitch(stateChange: VoiceStateUpdateEvent) = stateChange.state.channelId != stateChange.old?.channelId
}
