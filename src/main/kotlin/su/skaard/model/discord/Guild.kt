package su.skaard.model.discord

import javax.persistence.*

@Entity(name = "DiscordGuild")
@Table(name = "skaard_discord_guild")
class Guild(
    @Id
    @Column(name = "id")
    val id: ULong,

    @OneToMany(mappedBy = "guild", fetch = FetchType.LAZY)
    var members: List<GuildMember> = listOf(),

    @OneToMany(mappedBy = "guild", fetch = FetchType.LAZY)
    var channels: List<Channel> = listOf()
) {
}