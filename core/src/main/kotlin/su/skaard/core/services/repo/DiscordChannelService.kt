package su.skaard.core.services.repo

import org.springframework.stereotype.Service
import su.skaard.core.entities.discord.Channel
import su.skaard.core.entities.discord.Guild
import su.skaard.core.repositories.discord.ChannelRepository

@Service
class DiscordChannelService(
    private val repo: ChannelRepository,
    private val guildRepo: ISnowflakeRepoService<Guild>
) : IDiscordChannelService {
    override suspend fun getById(id: ULong): Channel? {
        return repo.searchById(id)
    }

    override suspend fun deleteById(id: ULong) {
        repo.deleteById(id)
    }

    override suspend fun save(entity: Channel): Channel {
        return repo.save(entity)
    }

    override suspend fun getByExternal(extEntity: dev.kord.core.entity.channel.Channel): Channel? {
        return getBySnowflake(extEntity.id)
    }

    override suspend fun createFromExternal(extEntity: dev.kord.core.entity.channel.Channel): Channel {
        val guild = guildRepo.getBySnowflake(requireNotNull(extEntity.data.guildId.value))
        val channel = Channel(
            id = extEntity.id.value,
            guild = requireNotNull(guild)
        )
        return repo.save(channel)
    }
}