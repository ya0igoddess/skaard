package su.skaard.core.synchronization.services

/**
 *  The interface providing the ability to synchronize some external entity [E] with it local representation [L].
 */
interface ISyncRepoService<L,E> {
    suspend fun getByExternal(extEntity: E): L?

    suspend fun createFromExternal(extEntity: E): L

    suspend fun findOrCreateFromExt(extEntity: E): L {
        return getByExternal(extEntity) ?: createFromExternal(extEntity)
    }
}