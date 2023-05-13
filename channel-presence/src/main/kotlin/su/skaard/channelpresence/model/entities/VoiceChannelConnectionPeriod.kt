package su.skaard.channelpresence.model.entities

import java.time.LocalDateTime
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table(name = "skaard_discord_voice_connection_period")
class VoiceChannelConnectionPeriod(
    @Id
    val id: Long = 0L,
    val channelId: Long,
    val memberId: Long,
    val connectionStart: LocalDateTime,
    val connectionEnd: LocalDateTime
)
