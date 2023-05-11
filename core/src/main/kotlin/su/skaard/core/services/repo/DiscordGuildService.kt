package su.skaard.core.services.repo

import org.springframework.stereotype.Service
import su.skaard.core.entities.discord.Guild
import su.skaard.core.repositories.discord.GuildsRepository

@Service
class DiscordGuildService(
    private val repo: GuildsRepository
): IGuildService {
    override suspend fun getById(id: ULong): Guild? {
        return repo.findById(id)
    }

    override suspend fun deleteById(id: ULong) {
        repo.deleteById(id)
    }

    override suspend fun save(entity: Guild): Guild {
        return repo.save(entity)
    }

    override suspend fun getByExternal(extEntity: dev.kord.core.entity.Guild): Guild? {
        return getBySnowflake(extEntity.id)
    }

    override suspend fun createFromExternal(extEntity: dev.kord.core.entity.Guild): Guild {
        val guild = Guild(
            id = extEntity.id.value
        )
        return repo.save(guild)
    }
}