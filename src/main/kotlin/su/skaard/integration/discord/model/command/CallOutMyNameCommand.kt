package su.skaard.integration.discord.model.command

import dev.kord.core.behavior.interaction.response.respond
import dev.kord.core.event.interaction.ChatInputCommandInteractionCreateEvent
import dev.kord.rest.builder.interaction.GlobalChatInputCreateBuilder
import org.springframework.stereotype.Component
import kotlin.random.Random

@Component(CallOutMyNameCommand.commandName)
class CallOutMyNameCommand : DiscordCommand {
    companion object { const val commandName = "call_out_my_name" }
    override val name: String
        get() = commandName
    override val description: String
        get() = "Calls out her name"
    override val builder: GlobalChatInputCreateBuilder.() -> Unit
        get() = { }

    /**
     * Responds Skaarl phrase.
     */
    override suspend fun execute(event: ChatInputCommandInteractionCreateEvent) {
        val deferred = event.interaction.deferPublicResponse()
        val randInt = Random.nextInt(2, 50)
        val resultingA = StringBuilder()
        (1..randInt).forEach { _ -> resultingA.append('a') }
        //event.interaction.respondPublic { content = "Sk${resultingA}rl!!" }
        val response = deferred.respond { content = "Sk${resultingA}rl!!"  }
    }
}