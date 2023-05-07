package su.skaard.core.services.repo

import su.skaard.core.entities.discord.Guild
import su.skaard.core.synchronization.services.IDiscordSyncRepoService

interface IGuildService: IDiscordSyncRepoService<Guild, dev.kord.core.entity.Guild>