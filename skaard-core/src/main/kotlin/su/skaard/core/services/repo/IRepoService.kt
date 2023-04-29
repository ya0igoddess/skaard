package su.skaard.core.services.repo
interface IRepoService<T> {

    fun getById(id: Long): T?


    fun deleteById(id: Long)

    fun save(entity: T): T
}