package su.skaard.repositories.discord

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import su.skaard.model.discord.Guild
import java.util.Optional

@Repository
interface GuildsRepository : JpaRepository<Guild, Long> {
    override fun findById(ID: Long): Optional<Guild>
    fun find(id: Long): Guild? = findById(id).orElse(null)
    override fun <S : Guild> save(entity: S): S
}