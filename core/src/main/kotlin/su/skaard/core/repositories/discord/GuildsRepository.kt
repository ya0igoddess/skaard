package su.skaard.core.repositories.discord

import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import su.skaard.core.entities.discord.DiscordGuild

@Repository
interface GuildsRepository : CoroutineCrudRepository<DiscordGuild, Long>
