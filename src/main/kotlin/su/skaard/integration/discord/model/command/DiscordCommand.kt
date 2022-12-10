package su.skaard.integration.discord.model.command

import dev.kord.core.event.interaction.ChatInputCommandInteractionCreateEvent
import dev.kord.rest.builder.interaction.GlobalChatInputCreateBuilder

interface DiscordCommand {
    val name: String
    val description: String
    val builder: GlobalChatInputCreateBuilder.() -> Unit

    /**
     * The method processing the command
     */
    suspend fun execute(event: ChatInputCommandInteractionCreateEvent)
}
