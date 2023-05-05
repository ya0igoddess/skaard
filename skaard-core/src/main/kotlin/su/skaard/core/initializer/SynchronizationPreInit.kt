package su.skaard.core.initializer

import dev.kord.core.Kord
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import su.skaard.core.synchronization.services.SynchronizationService

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
class SynchronizationPreInit(
    private val synchronizationService: SynchronizationService
) : IKordPreInitializer {
    override fun preInitialize(kord: Kord) {
        synchronizationService.synchronizeData(kord)
    }
}