package su.skaard.core.entities.discord
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table(name = "skaard_discord_channel")
class Channel(
    @Id
    val id: Long,
    val guildId: Long
)
