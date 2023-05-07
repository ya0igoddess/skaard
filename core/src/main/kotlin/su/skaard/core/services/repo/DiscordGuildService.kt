package su.skaard.core.services.repo

import org.springframework.stereotype.Service
import su.skaard.core.entities.discord.Guild
import su.skaard.core.repositories.discord.GuildsRepository

@Service
class DiscordGuildService(
    private val repo: GuildsRepository
): IGuildService {
    override fun getById(id: Long): Guild? {
        return repo.searchById(id)
    }

    override fun deleteById(id: Long) {
        repo.deleteById(id)
    }

    override fun save(entity: Guild): Guild {
        return repo.save(entity)
    }

    override fun getByExternal(extEntity: dev.kord.core.entity.Guild): Guild? {
        return getBySnowflake(extEntity.id)
    }

    override fun createFromExternal(extEntity: dev.kord.core.entity.Guild): Guild {
        val guild = Guild(
            id = extEntity.id.value
        )
        return repo.save(guild)
    }
}