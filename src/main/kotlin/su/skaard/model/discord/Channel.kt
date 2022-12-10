package su.skaard.model.discord

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

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
