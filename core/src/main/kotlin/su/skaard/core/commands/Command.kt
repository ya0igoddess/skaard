package su.skaard.core.commands

import org.springframework.stereotype.Component

/**
 * Indicates that an annotated class must be registered as Command in SkaardApp
 */
@Component
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Command()
