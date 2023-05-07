package su.skaard.core.entities.discord

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity(name = "DiscordChannel")
@Table(name = "skaard_discord_channel")
class Channel(
    @Id
    @Column(name = "id")
    val id: ULong,

    @JoinColumn(name = "guild")
    @ManyToOne
    val guild: Guild
)
