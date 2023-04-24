package su.skaard.core.entities.discord

import jakarta.persistence.*

@Entity(name = "DiscordGuild")
@Table(name = "skaard_discord_guild")
class Guild(
    @Id
    @Column(name = "id")
    val id: ULong,

    @OneToMany(mappedBy = "guild", fetch = FetchType.LAZY)
    var members: List<GuildMember> = mutableListOf(),

    @OneToMany(mappedBy = "guild", fetch = FetchType.LAZY)
    var channels: List<Channel> = mutableListOf()
)
