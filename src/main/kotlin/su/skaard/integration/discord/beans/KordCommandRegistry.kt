package su.skaard.integration.discord.beans

import dev.kord.core.Kord
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component
import su.skaard.integration.discord.model.command.DiscordCommand

@Component
class KordCommandRegistry @Autowired constructor(
    private val applicationContext: ApplicationContext
) {
    private val commandsMap: MutableMap<String, DiscordCommand> = mutableMapOf()
    val commands = commandsMap as Map<String, DiscordCommand>

    suspend fun registerCommands(kord: Kord) {
        val commands = applicationContext.getBeansOfType(DiscordCommand::class.java)
        commands.values.forEach {
            kord.createGlobalChatInputCommand(
                name = it.name,
                description = it.description,
                builder = it.builder
            )
            commandsMap[it.name] = it
        }
    }
}