package su.skaard.core.services.repo

interface IDiscordSyncRepoService<T, E>: ISnowflakeRepoService<T>, ISyncRepoService<T, E>