package su.skaard.model.discord

import javax.persistence.*

@Entity(name = "DiscordGuildMember")
@Table(name = "skaard_discord_guild_member")
public class GuildMember(
    @Id
    @GeneratedValue
    @Column(name = "id")
    val id: ULong,

    @JoinColumn(name = "discord_user")
    @ManyToOne
    val discordUser: DiscordUser,

    @JoinColumn(name = "guild")
    @ManyToOne
    val guild: Guild
)
