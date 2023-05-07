package su.skaard.core.synchronization.services

import su.skaard.core.services.repo.ISnowflakeRepoService

interface IDiscordSyncRepoService<T, E>: ISnowflakeRepoService<T>, ISyncRepoService<T, E>