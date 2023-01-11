package su.skaard.integration.discord.beans

import dev.kord.core.Kord
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import su.skaard.integration.discord.model.command.DiscordCommand

@Component
class KordCommandRegistryImpl @Autowired constructor(
    override val commands: Map<String, DiscordCommand>
) : KordCommandRegistry {
    override fun registerCommands(kord: Kord) = runBlocking {
        commands.values.forEach {
            kord.createGlobalChatInputCommand(
                name = it.name,
                description = it.description,
                builder = it.builder
            )
        }
    }
}
