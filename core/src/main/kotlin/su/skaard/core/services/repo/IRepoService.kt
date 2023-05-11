package su.skaard.core.services.repo
interface IRepoService<T, ID> {

    suspend fun getById(id: ID): T?


    suspend fun deleteById(id: ID)

    suspend fun save(entity: T): T
}