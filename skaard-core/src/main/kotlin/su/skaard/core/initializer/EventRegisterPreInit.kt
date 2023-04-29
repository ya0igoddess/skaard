package su.skaard.core.initializer

import dev.kord.core.Kord
import dev.kord.core.event.Event
import dev.kord.core.on
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import su.skaard.core.handlers.DiscordEventHandler

@Component
@Order(1)
class EventRegisterPreInit(
    private val eventHandlers: List<DiscordEventHandler>
) : IKordPreInitializer {
    override fun preInitialize(kord: Kord) {
        kord.on<Event> { eventHandlers.forEach { it.handle(this) } }
    }
}