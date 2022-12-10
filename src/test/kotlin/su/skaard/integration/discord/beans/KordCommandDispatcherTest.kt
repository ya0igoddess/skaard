package su.skaard.integration.discord.beans

import dev.kord.core.event.interaction.ChatInputCommandInteractionCreateEvent
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

import su.skaard.integration.discord.model.command.DiscordCommand

internal class KordCommandDispatcherTest {

    val commandA = mockk<DiscordCommand>(relaxed = true)
    val commandB = mockk<DiscordCommand>(relaxed = true)
    private val registry = KordCommandRegistry(mapOf(
            "commandA" to commandA,
            "commandB" to commandB
    ))
    private val dispatcher = KordCommandDispatcher(registry)
    @Test
    fun handleCommandEvent() = runBlocking {
        val event = spyk(mockk<ChatInputCommandInteractionCreateEvent>(relaxed = true))
        every { event.interaction.getProperty("invokedCommandName") } returns "commandB"
        dispatcher.handleCommandEvent(event)
        coVerify { commandB.execute(event) }
    }
}