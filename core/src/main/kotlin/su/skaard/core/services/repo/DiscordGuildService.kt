package su.skaard.core.services.repo

import org.springframework.stereotype.Service
import su.skaard.core.entities.discord.Guild
import su.skaard.core.repositories.discord.GuildsRepository
import su.skaard.core.utils.lvalue

@Service
class DiscordGuildService(
    private val repo: GuildsRepository
): IGuildService {
    override suspend fun getById(id: Long): Guild? {
        return repo.findById(id)
    }

    override suspend fun deleteById(id: Long) {
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
            id = extEntity.id.lvalue
        )
        return repo.save(guild)
    }
}