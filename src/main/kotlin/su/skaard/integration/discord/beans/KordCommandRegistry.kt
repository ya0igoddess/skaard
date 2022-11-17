package su.skaard.integration.discord.beans

import dev.kord.core.Kord
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import su.skaard.integration.discord.model.command.DiscordCommand

@Component
class KordCommandRegistry @Autowired constructor(
    val commands: Map<String, DiscordCommand>
) {
    suspend fun registerCommands(kord: Kord) {
        commands.values.forEach {
            kord.createGlobalChatInputCommand(
                name = it.name,
                description = it.description,
                builder = it.builder
            )
        }
    }
}