package su.skaard.core.initializer

import dev.kord.core.Kord

interface IKordPreInitializer {

    /**
     * The action (such as command registration) that is applied to Kord instance before login.
     */
    fun preInitialize(kord: Kord)
}