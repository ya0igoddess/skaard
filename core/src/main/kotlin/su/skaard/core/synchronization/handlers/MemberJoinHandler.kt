package su.skaard.core.synchronization.handlers

import dev.kord.core.event.Event
import dev.kord.core.event.guild.MemberJoinEvent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import su.skaard.core.handlers.DiscordEventHandler
import su.skaard.core.synchronization.services.ISynchronizationService

@Component
class MemberJoinHandler @Autowired constructor(
    private val ISynchronizationService: ISynchronizationService
) : DiscordEventHandler {
    override suspend fun handle(event: Event) {
        if (event !is MemberJoinEvent) return
        ISynchronizationService.handleMemberJoinEvent(event)
    }
}
