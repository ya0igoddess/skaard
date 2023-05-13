package su.skaard.core.services.repo

import org.springframework.stereotype.Service
import su.skaard.core.entities.discord.Channel
import su.skaard.core.entities.discord.Guild
import su.skaard.core.repositories.discord.ChannelRepository
import su.skaard.core.utils.lvalue

@Service
class DiscordChannelService(
    private val repo: ChannelRepository,
    private val guildRepo: ISnowflakeRepoService<Guild>
) : IDiscordChannelService {
    override suspend fun getById(id: Long): Channel? {
        return repo.findById(id)
    }

    override suspend fun deleteById(id: Long) {
        repo.deleteById(id)
    }

    override suspend fun save(entity: Channel): Channel {
        return repo.save(entity)
    }

    override suspend fun getByExternal(extEntity: dev.kord.core.entity.channel.Channel): Channel? {
        return getBySnowflake(extEntity.id)
    }

    override suspend fun createFromExternal(extEntity: dev.kord.core.entity.channel.Channel): Channel {
        val guild = requireNotNull(guildRepo.getBySnowflake(requireNotNull(extEntity.data.guildId.value)))
        val channel = Channel(
            id = extEntity.id.lvalue,
            guildId = guild.id
        )
        return repo.save(channel)
    }
}