package su.skaard.channelpresence.model.periods

import dev.kord.common.entity.Snowflake
import java.time.LocalDateTime

data class ClosedVoiceConnection(
    val channelId: Snowflake,
    val userId: Snowflake,
    val beginTime: LocalDateTime,
    val endTime: LocalDateTime = LocalDateTime.now()
)
