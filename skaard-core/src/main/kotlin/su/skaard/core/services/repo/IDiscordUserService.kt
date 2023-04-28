package su.skaard.core.services.repo

import dev.kord.core.entity.User
import su.skaard.core.entities.discord.DiscordUser
import su.skaard.core.synchronization.services.IDiscordSyncRepoService

interface IDiscordUserService: IDiscordSyncRepoService<DiscordUser, User>