package su.skaard.channelpresence.model.periods

import dev.kord.common.entity.Snowflake
import java.time.LocalDateTime

data class OpenedVoiceConnection(
    val channelId: Snowflake,
    val userId: Snowflake,
    val beginTime: LocalDateTime = LocalDateTime.now()
) {
    fun close() = ClosedVoiceConnection(channelId, userId, beginTime)
}
