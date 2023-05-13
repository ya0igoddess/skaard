package su.skaard.channelpresence.services

import dev.kord.common.entity.Snowflake
import io.mockk.*
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import su.skaard.core.repositories.discord.ChannelRepository
import su.skaard.core.repositories.discord.DiscordUserRepository
import su.skaard.core.repositories.discord.GuildMemberRepository
import su.skaard.channelpresence.repositories.VoiceChannelConnectionPeriodRepository
import su.skaard.channelpresence.services.ConnectionPeriodRegistryService
import su.skaard.core.entities.discord.GuildMember

internal class ConnectionPeriodRegistryServiceTest {
    private val guildMemberRepository: GuildMemberRepository = mockk(relaxed = true)
    private val channelRepository: ChannelRepository = mockk(relaxed = true)
    private val voiceChannelConnectionPeriodRepository: VoiceChannelConnectionPeriodRepository = mockk()

    private val service = ConnectionPeriodRegistryService(
        guildMemberRepository,
        channelRepository,
        voiceChannelConnectionPeriodRepository
    )

    @Test
    fun `opens new connection`() = runTest {
        service.openConnection("1", Snowflake("123"), Snowflake("123"))
        assertEquals(true, service.openedConnections.containsKey("1"))
    }

    @Test
    fun `closes the connection`() = runTest {
        val connId = "close connection"
        coEvery { voiceChannelConnectionPeriodRepository.save(any()) } returns mockk()
        coEvery { channelRepository.findById(any()) } returns mockk(relaxed = true)
        coEvery { guildMemberRepository.getByGuildIdAndDiscordUserId(any(), any()) } returns mockk(relaxed = true)

        service.openConnection(connId, Snowflake("123"), Snowflake("123"))
        service.closeConnection(connId)
        assertEquals(false, service.openedConnections.containsKey(connId))
        coVerify { voiceChannelConnectionPeriodRepository.save(any()) }
        confirmVerified(voiceChannelConnectionPeriodRepository)
    }

    @Test
    fun `doesn't reopen connections`() = runTest {
        val connectionId = "doesn't reopen connections"
        service.openConnection(connectionId, Snowflake("123"), Snowflake("123"))

        assertThrows(IllegalStateException::class.java) {
            runBlocking { service.openConnection(connectionId, Snowflake("123"), Snowflake("3423")) }
        }
    }
}
