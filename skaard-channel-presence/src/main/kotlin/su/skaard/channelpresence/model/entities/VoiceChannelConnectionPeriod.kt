package su.skaard.channelpresence.model.entities

import java.time.LocalDateTime
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import su.skaard.core.entities.discord.Channel
import su.skaard.core.entities.discord.GuildMember

@Entity(name = "VoiceChannelConnectionPeriod")
@Table(name = "skaard_discord_voice_connection_period")
class VoiceChannelConnectionPeriod(
    @Id
    @GeneratedValue
    val id: ULong,

    @JoinColumn(name = "channel_id")
    @ManyToOne
    val channel: Channel,

    @JoinColumn(name = "member_id")
    @ManyToOne
    val member: GuildMember,

    @Column(name = "begin_time")
    val connectionStart: LocalDateTime,

    @Column(name = "end_time")
    val connectionEnd: LocalDateTime
)
