package su.skaard.core.commands.services

import dev.kord.core.event.interaction.ChatInputCommandInteractionCreateEvent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class KordCommandDispatcher @Autowired constructor(
    private val commandRegistry: IKordCommandRegistry
) : IKordCommandDispatcher {
    override suspend fun handleCommandEvent(chatInputEvent: ChatInputCommandInteractionCreateEvent) {
        val commandName = chatInputEvent.interaction.invokedCommandName
        commandRegistry.commands[commandName]?.execute(chatInputEvent)
    }
}
