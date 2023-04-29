package su.skaard.core.commands.services

import dev.kord.core.Kord
import su.skaard.core.commands.DiscordCommand

interface IKordCommandRegistry {
    val commands: Map<String, DiscordCommand>
    fun registerCommands(kord: Kord)
}
