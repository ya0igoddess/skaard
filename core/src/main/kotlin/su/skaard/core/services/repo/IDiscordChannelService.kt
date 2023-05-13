package su.skaard.core.services.repo

import su.skaard.core.entities.discord.DiscordChannel
import su.skaard.core.synchronization.services.IDiscordSyncRepoService

interface IDiscordChannelService: IDiscordSyncRepoService<DiscordChannel, dev.kord.core.entity.channel.Channel>