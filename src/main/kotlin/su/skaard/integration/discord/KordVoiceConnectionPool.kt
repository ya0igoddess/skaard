package su.skaard.integration.discord

import dev.kord.common.entity.Snowflake
import dev.kord.core.event.user.VoiceStateUpdateEvent
import org.springframework.stereotype.Component

@Component
class KordVoiceConnectionPool {
    private val connections: MutableMap<String, OpenedVoiceConnection> = mutableMapOf()

    //TODO rewrite in concurrent style
    fun handleVoiceChange(stateChange:VoiceStateUpdateEvent) {
        this.closeConnection(stateChange.old?.sessionId)
        val state = stateChange.state
        this.openConnection(state.sessionId, state.channelId!!, state.userId)
    }

    private fun openConnection(connectionId:String, channelId:Snowflake, userId:Snowflake) {
        if (connections.containsKey(connectionId)) {
            throw IllegalStateException("Attempt to open the already opened connection (connection ID match)")
        }
        connections[connectionId] = OpenedVoiceConnection(channelId, userId)
    }

    private fun closeConnection(connectionId: String?) {
        val connection = connections.remove(connectionId)?.close()
        throw NotImplementedError()
    }

    fun connections(): Map<String, OpenedVoiceConnection> {
        return connections
    }
}