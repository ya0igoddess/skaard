package su.skaard.core.services.repo

import dev.kord.common.entity.Snowflake

interface ISnowflakeRepoService<T>: IRepoService<T, ULong> {
    suspend fun getBySnowflake(id: Snowflake): T? {
        return getById(id.value)
    }

    suspend fun deleteBySnowflake(id: Snowflake) {
        deleteById(id.value)
    }
}