package su.skaard.core.services.repo
interface IRepoService<T> {

    fun getById(id: Long): T?


    fun deleteById(id: Long): T?

    fun save(entity: Long): T
}