package su.skaard.integration.discord.model.command

import dev.kord.core.behavior.interaction.response.respond
import dev.kord.core.event.interaction.ChatInputCommandInteractionCreateEvent
import dev.kord.rest.builder.interaction.GlobalChatInputCreateBuilder
import dev.kord.rest.builder.interaction.int
import dev.kord.rest.builder.interaction.integer
import org.springframework.stereotype.Component
import su.skaard.core.commands.DiscordCommand

@Component(CreatePermutationCommand.commandName)
class CreatePermutationCommand : DiscordCommand {
    companion object {
        const val commandName = "create_permutation"
    }

    override val name: String
        get() = commandName
    override val description: String
        get() = "Creates a permutation with the given number of elements"
    override val builder: GlobalChatInputCreateBuilder.() -> Unit
        get() = {
            integer("n", "Number of elements") { required = true }
        }

    override suspend fun execute(event: ChatInputCommandInteractionCreateEvent) {
        val deferred = event.interaction.deferPublicResponse()
        val n = event.interaction.command.integers["n"]
        requireNotNull(n)
        val permutation = (1..n).toList().shuffled()
        deferred.respond { permutation.toString() }
    }
}
