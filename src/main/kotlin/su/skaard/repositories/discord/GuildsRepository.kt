package su.skaard.repositories.discord

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import su.skaard.model.discord.Guild
import java.util.Optional

@Repository
interface GuildsRepository : JpaRepository<Guild, Long> {
    //override fun findById(ID: Long): Optional<Guild>
    fun searchById(id: Long): Guild?
    override fun <S : Guild> save(entity: S): S
}
