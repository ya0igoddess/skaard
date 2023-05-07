package su.skaard.core.services.repo

import dev.kord.common.entity.Snowflake

interface ISnowflakeRepoService<T>: IRepoService<T> {
    fun getBySnowflake(id: Snowflake): T? {
        return getById(id.value.toLong())
    }

    fun deleteBySnowflake(id: Snowflake) {
        deleteById(id.value.toLong())
    }
}