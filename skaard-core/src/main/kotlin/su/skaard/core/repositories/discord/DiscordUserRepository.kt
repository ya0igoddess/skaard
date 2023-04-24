package su.skaard.core.repositories.discord

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import su.skaard.core.entities.discord.DiscordUser
import java.util.*

@Repository
interface DiscordUserRepository : JpaRepository<DiscordUser, Long> {
    override fun findById(id: Long): Optional<DiscordUser>
    fun searchById(id: Long): DiscordUser?
    override fun <S : DiscordUser> save(entity: S): S
}
