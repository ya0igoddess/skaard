package su.skaard.core.services.repo

import dev.kord.core.entity.User
import org.springframework.stereotype.Service
import su.skaard.core.entities.discord.DiscordUser
import su.skaard.core.repositories.discord.DiscordUserRepository
import su.skaard.core.utils.lvalue

@Service
class DiscordUserService(
    private val repository: DiscordUserRepository
): IDiscordUserService {
    override suspend fun getById(id: Long): DiscordUser? {
        return repository.findById(id)
    }

    override suspend fun deleteById(id: Long) {
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
            id = it.id.lvalue,
            name = it.username
        ) }
        return repository.save(user)
    }
}