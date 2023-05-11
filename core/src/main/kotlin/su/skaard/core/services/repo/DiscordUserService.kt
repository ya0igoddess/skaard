package su.skaard.core.services.repo

import dev.kord.core.entity.User
import org.springframework.stereotype.Service
import su.skaard.core.entities.discord.DiscordUser
import su.skaard.core.repositories.discord.DiscordUserRepository

@Service
class DiscordUserService(
    private val repository: DiscordUserRepository
): IDiscordUserService {
    override suspend fun getById(id: ULong): DiscordUser? {
        return repository.findById(id)
    }

    override suspend fun deleteById(id: ULong) {
        repository.deleteById(id)
    }

    override suspend fun save(entity: DiscordUser): DiscordUser {
        return repository.save(entity)
    }

    override suspend fun getByExternal(extEntity: User): DiscordUser? {
        return getBySnowflake(extEntity.id)
    }

    override suspend fun createFromExternal(extEntity: User): DiscordUser {
        val user = extEntity.let { DiscordUser(
            id = it.id.value,
            name = it.username
        ) }
        return repository.save(user)
    }
}