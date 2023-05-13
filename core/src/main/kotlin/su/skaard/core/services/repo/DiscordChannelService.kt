package su.skaard.core.services.repo

import org.springframework.stereotype.Service
import su.skaard.core.entities.discord.DiscordChannel
import su.skaard.core.entities.discord.DiscordGuild
import su.skaard.core.repositories.discord.ChannelRepository
import su.skaard.core.utils.lvalue

@Service
class DiscordChannelService(
    private val repo: ChannelRepository,
    private val guildRepo: ISnowflakeRepoService<DiscordGuild>
) : IDiscordChannelService {
    override suspend fun getById(id: Long): DiscordChannel? {
        return repo.findById(id)
    }

    override suspend fun deleteById(id: Long) {
        repo.deleteById(id)
    }

    override suspend fun save(entity: DiscordChannel): DiscordChannel {
        return repo.save(entity)
    }

    override suspend fun getByExternal(extEntity: dev.kord.core.entity.channel.Channel): DiscordChannel? {
        return getBySnowflake(extEntity.id)
    }

    override suspend fun createFromExternal(extEntity: dev.kord.core.entity.channel.Channel): DiscordChannel {
        val guildId = requireNotNull(extEntity.data.guildId.value)
        val guild = requireNotNull(guildRepo.getBySnowflake(guildId))
        val channel = DiscordChannel(
            id = extEntity.id.lvalue,
            guildId = guild.id,
            isNew = true
        )
        return repo.save(channel)
    }
}