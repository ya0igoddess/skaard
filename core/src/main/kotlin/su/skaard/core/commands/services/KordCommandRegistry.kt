package su.skaard.core.commands.services

import dev.kord.core.Kord
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import su.skaard.core.commands.DiscordCommand

@Component
class KordCommandRegistry @Autowired constructor(
    override val commands: Map<String, DiscordCommand>
) : IKordCommandRegistry {
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
