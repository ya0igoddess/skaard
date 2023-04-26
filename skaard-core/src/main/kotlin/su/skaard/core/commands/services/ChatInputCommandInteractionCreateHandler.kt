package su.skaard.core.commands.services

import dev.kord.core.event.Event
import dev.kord.core.event.interaction.ChatInputCommandInteractionCreateEvent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import su.skaard.core.handlers.DiscordEventHandler

@Component
class ChatInputCommandInteractionCreateHandler @Autowired constructor(
    private val commandDispatcher: IKordCommandDispatcher
) : DiscordEventHandler {
    override suspend fun handle(event: Event) {
        if (event !is ChatInputCommandInteractionCreateEvent) return
        commandDispatcher.handleCommandEvent(event)
    }
}
