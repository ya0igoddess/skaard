package su.skaard.integration.discord.beans.handlers

import dev.kord.core.event.Event

interface DiscordEventHandler {
    suspend fun handle(event: Event)
}
