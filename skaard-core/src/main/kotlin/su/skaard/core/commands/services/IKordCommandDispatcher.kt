package su.skaard.core.commands.services

import dev.kord.core.event.interaction.ChatInputCommandInteractionCreateEvent

interface IKordCommandDispatcher {
    suspend fun handleCommandEvent(chatInputEvent: ChatInputCommandInteractionCreateEvent)
}