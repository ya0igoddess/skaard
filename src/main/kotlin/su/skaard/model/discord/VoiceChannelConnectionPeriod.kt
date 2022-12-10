package su.skaard.model.discord

import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

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
