package su.skaard.core.entities.discord

import org.springframework.data.annotation.Id
import org.springframework.data.domain.Persistable
import org.springframework.data.relational.core.mapping.Table

@Table(name = "skaard_discord_guild_member")
class GuildMember(
    @Id
    val id: Long = 0L,
    val discordUserId: Long,
    val guildId: Long,
) : Persistable<Long> {
    override fun getId(): Long? {
        return id
    }

    override fun isNew(): Boolean {
        return id == 0L
    }
}
