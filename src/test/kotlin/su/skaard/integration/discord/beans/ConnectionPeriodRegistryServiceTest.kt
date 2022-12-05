package su.skaard.integration.discord.beans

import dev.kord.common.entity.Snowflake
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import su.skaard.repositories.discord.ChannelRepository
import su.skaard.repositories.discord.DiscordUserRepository
import su.skaard.repositories.discord.GuildMemberRepository
import su.skaard.repositories.discord.VoiceChannelConnectionPeriodRepository

internal class ConnectionPeriodRegistryServiceTest {
    private val userRepository: DiscordUserRepository = mockk(relaxed = true)
    private val guildMemberRepository: GuildMemberRepository = mockk(relaxed = true)
    private val channelRepository: ChannelRepository = mockk(relaxed = true)
    private val voiceChannelConnectionPeriodRepository: VoiceChannelConnectionPeriodRepository = mockk()

    private val service = ConnectionPeriodRegistryService(
        userRepository,
        guildMemberRepository,
        channelRepository,
        voiceChannelConnectionPeriodRepository
    )

    @Test
    fun `opens new connection`() {
        service.openConnection("1", Snowflake("123"), Snowflake("123"))
        assertEquals(true, service.openedConnections.containsKey("1"))
    }

    @Test
    fun `closes the connection`() {
        val connId = "close connection"
        every { voiceChannelConnectionPeriodRepository.save(any()) } returns mockk()

        service.openConnection(connId, Snowflake("123"), Snowflake("123"))
        service.closeConnection(connId)
        assertEquals(false, service.openedConnections.containsKey(connId))
        verify { voiceChannelConnectionPeriodRepository.save(any()) }
        confirmVerified(voiceChannelConnectionPeriodRepository)
    }

    @Test
    fun `doesn't reopen connections`() {
        val connectionId = "doesn't reopen connections"
        service.openConnection(connectionId, Snowflake("123"),Snowflake("123"))
        assertThrows(IllegalStateException::class.java) {
            service.openConnection(connectionId, Snowflake("123"), Snowflake("3423"))
        }
    }
}