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
    override fun getById(id: Long): Channel? {
        return repo.searchById(id)
    }

    override fun deleteById(id: Long) {
        repo.deleteById(id)
    }

    override fun save(entity: Channel): Channel {
        return repo.save(entity)
    }

    override fun getByExternal(extEntity: dev.kord.core.entity.channel.Channel): Channel? {
        return getBySnowflake(extEntity.id)
    }

    override fun createFromExternal(extEntity: dev.kord.core.entity.channel.Channel): Channel {
        val guild = guildRepo.getBySnowflake(requireNotNull(extEntity.data.guildId.value))
        val channel = Channel(
            id = extEntity.id.value,
            guild = requireNotNull(guild)
        )
        return repo.save(channel)
    }
}