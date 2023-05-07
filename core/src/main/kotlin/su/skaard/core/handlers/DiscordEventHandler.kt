package su.skaard.core.handlers

import dev.kord.core.event.Event

interface DiscordEventHandler {
    suspend fun handle(event: Event)
}
