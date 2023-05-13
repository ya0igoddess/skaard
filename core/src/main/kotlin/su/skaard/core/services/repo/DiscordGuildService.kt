package su.skaard.core.services.repo

import org.springframework.stereotype.Service
import su.skaard.core.entities.discord.DiscordGuild
import su.skaard.core.repositories.discord.GuildsRepository
import su.skaard.core.utils.lvalue

@Service
class DiscordGuildService(
    private val repo: GuildsRepository
): IGuildService {
    override suspend fun getById(id: Long): DiscordGuild? {
        return repo.findById(id)
    }

    override suspend fun deleteById(id: Long) {
        repo.deleteById(id)
    }

    override suspend fun save(entity: DiscordGuild): DiscordGuild {
        return repo.save(entity)
    }

    override suspend fun getByExternal(extEntity: dev.kord.core.entity.Guild): DiscordGuild? {
        return getBySnowflake(extEntity.id)
    }

    override suspend fun createFromExternal(extEntity: dev.kord.core.entity.Guild): DiscordGuild {
        val guild = DiscordGuild(
            id = extEntity.id.lvalue,
            isNew = true
        )
        return repo.save(guild)
    }
}