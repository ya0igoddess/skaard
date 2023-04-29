package su.skaard.core.services.repo

import dev.kord.core.entity.User
import org.springframework.stereotype.Service
import su.skaard.core.entities.discord.DiscordUser
import su.skaard.core.repositories.discord.DiscordUserRepository

@Service
class DiscordUserService(
    private val repository: DiscordUserRepository
): IDiscordUserService {
    override fun getById(id: Long): DiscordUser? {
        return repository.searchById(id)
    }

    override fun deleteById(id: Long) {
        repository.deleteById(id)
    }

    override fun save(entity: DiscordUser): DiscordUser {
        return repository.save(entity)
    }

    override fun getByExternal(extEntity: User): DiscordUser? {
        return getBySnowflake(extEntity.id)
    }

    override fun createFromExternal(extEntity: User): DiscordUser {
        val user = extEntity.let { DiscordUser(
            id = it.id.value,
            name = it.username
        ) }
        return repository.save(user)
    }
}