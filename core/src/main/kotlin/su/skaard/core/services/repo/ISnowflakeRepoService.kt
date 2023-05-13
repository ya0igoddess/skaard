package su.skaard.core.services.repo

import dev.kord.common.entity.Snowflake
import su.skaard.core.utils.lvalue

interface ISnowflakeRepoService<T>: IRepoService<T, Long> {
    suspend fun getBySnowflake(id: Snowflake): T? {
        return getById(id.lvalue)
    }

    suspend fun deleteBySnowflake(id: Snowflake) {
        deleteById(id.lvalue)
    }
}