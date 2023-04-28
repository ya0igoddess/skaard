package su.skaard.core.services.repo

/**
 *  The interface providing the ability to synchronize some external entity [E] with it local representation [L].
 */
interface ISyncRepoService<L,E> {
    fun getByExternal(extEntity: E): L?

    fun createFromExternal(extEntity: E): L

    fun findOrCreateFromExt(extEntity: E): L {
        return getByExternal(extEntity) ?: createFromExternal(extEntity)
    }
}