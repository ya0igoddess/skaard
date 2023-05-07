package su.skaard.core.repositories.discord

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import su.skaard.core.entities.discord.Guild

@Repository
interface GuildsRepository : JpaRepository<Guild, Long> {
    // override fun findById(ID: Long): Optional<Guild>
    fun searchById(id: Long): Guild?
    override fun <S : Guild> save(entity: S): S
}
