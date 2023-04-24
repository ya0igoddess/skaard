package su.skaard.service

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import su.skaard.core.entities.discord.Channel
import su.skaard.core.entities.discord.Guild
import su.skaard.core.entities.discord.GuildMember
import su.skaard.model.discord.VoiceChannelConnectionPeriod
import su.skaard.repositories.discord.VoiceChannelConnectionPeriodRepository

internal class ConnectionPeriodServiceTest {
    private val connectionsRepo = mockk<VoiceChannelConnectionPeriodRepository>(relaxed = true)
    private val service = ConnectionPeriodService(connectionsRepo)

    private val guild = mockk<Guild>(relaxed = true)
    private val channel = Channel(0UL, guild)

    private val memberA = mockk<GuildMember>()
    private val memberB = mockk<GuildMember>()

    private fun mockkPeriod(member: GuildMember): VoiceChannelConnectionPeriod {
        val connectionPeriod = mockk<VoiceChannelConnectionPeriod>(relaxed = true)
        every { connectionPeriod.getProperty("member") } returns member
        every { connectionPeriod.getProperty("channel") } returns channel
        return connectionPeriod
    }

    @Test
    fun getChannelConnectionStat() {
        val periodsA = listOf(mockkPeriod(memberA), mockkPeriod(memberA))
        val periodsB = listOf(mockkPeriod(memberB), mockkPeriod(memberB), mockkPeriod(memberB))
        every {
            connectionsRepo.getAllByChannelAndConnectionStartAfterAndConnectionEndBefore(
                channel,
                any(),
                any()
            )
        } returns mutableListOf<VoiceChannelConnectionPeriod>().also {
            it.addAll(periodsA)
            it.addAll(periodsB)
        }
        val resultMap = service.getChannelConnectionStat(channel)
        assertArrayEquals(arrayOf(periodsA), arrayOf(resultMap[memberA]))
        assertArrayEquals(arrayOf(periodsB), arrayOf(resultMap[memberB]))
    }
}
