package su.skaard.core.services.repo

import su.skaard.core.entities.discord.DiscordGuild
import su.skaard.core.synchronization.services.IDiscordSyncRepoService

interface IGuildService: IDiscordSyncRepoService<DiscordGuild, dev.kord.core.entity.Guild>