package su.skaard.core.entities.discord

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.domain.Persistable
import org.springframework.data.relational.core.mapping.Table

@Table(name = "skaard_discord_user")
class DiscordUser(
    @Id
    val id: Long,
    var name: String,
    @Transient private val isNew: Boolean = false
) : Persistable<Long> {
    override fun getId(): Long {
        return id
    }

    override fun isNew(): Boolean {
        return isNew
    }
}
