package su.skaard.channelpresence.handlers

import dev.kord.common.entity.Snowflake
import dev.kord.core.event.user.VoiceStateUpdateEvent
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import su.skaard.channelpresence.services.ConnectionPeriodRegistryService

internal class VoiceStateUpdateHandlerTest {
    private val connectionPeriodRegistryService = mockk<ConnectionPeriodRegistryService>(relaxed = true)

    private val handler = VoiceStateUpdateHandler(connectionPeriodRegistryService)

    @Test
    fun `opens new connection when the channel is switched`() = runBlocking {
        val event = mockk<VoiceStateUpdateEvent>(relaxed = true)
        every { event.old?.channelId } returns Snowflake("123")
        every { event.state.channelId } returns Snowflake("321")
        handler.handle(event)
        coVerify {
            connectionPeriodRegistryService.closeConnection(any())
            connectionPeriodRegistryService.openConnection(any(), any(), any())
        }
    }

    @Test
    fun `does nothing when there is no channel changes`() = runBlocking {
        val event = mockk<VoiceStateUpdateEvent>()
        every { event.old?.channelId } returns Snowflake("123")
        every { event.state.channelId } returns Snowflake("123")
        handler.handle(event)
        confirmVerified(connectionPeriodRegistryService)
    }
}