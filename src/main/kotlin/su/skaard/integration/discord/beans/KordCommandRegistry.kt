package su.skaard.integration.discord.beans

import dev.kord.core.Kord
import su.skaard.integration.discord.model.command.DiscordCommand

interface KordCommandRegistry {
    val commands: Map<String, DiscordCommand>
    fun registerCommands(kord: Kord)
}
