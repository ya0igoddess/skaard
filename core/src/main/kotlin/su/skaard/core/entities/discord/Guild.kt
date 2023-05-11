package su.skaard.core.entities.discord

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table(name = "skaard_discord_guild")
class Guild(
    @Id
    val id: ULong,
)
