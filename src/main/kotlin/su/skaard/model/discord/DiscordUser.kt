package su.skaard.model.discord

import javax.persistence.*

@Entity(name = "DiscordUser")
@Table(name = "skaard_discord_user")
class DiscordUser(
    @Id
    @Column(name = "id")
    var id: ULong,

    @Column(name = "name")
    var name: String,

    @OneToMany(mappedBy = "discordUser", fetch = FetchType.LAZY)
    var membership: List<GuildMember> = listOf()
) {

}