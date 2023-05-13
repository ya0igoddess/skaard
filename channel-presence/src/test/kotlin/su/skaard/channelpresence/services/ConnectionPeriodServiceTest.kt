package su.skaard.channelpresence.services

import io.mockk.InternalPlatformDsl.toArray
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import su.skaard.channelpresence.model.entities.VoiceChannelConnectionPeriod
import su.skaard.channelpresence.repositories.VoiceChannelConnectionPeriodRepository
import su.skaard.core.entities.discord.DiscordChannel
import su.skaard.core.entities.discord.DiscordGuild
import su.skaard.core.entities.discord.GuildMember
import java.time.LocalDate
import java.time.LocalDateTime

internal class ConnectionPeriodServiceTest {
    private val connectionsRepo = mockk<VoiceChannelConnectionPeriodRepository>(relaxed = true)
    private val service = ConnectionPeriodService(connectionsRepo)

    private val guild = mockk<DiscordGuild>(relaxed = true)
    private val channel = DiscordChannel(0L, guild.id)

    private val memberA = GuildMember(1L, 3L, 4L)
    private val memberB = GuildMember(2L, 5L, 4L)

    private fun mockkPeriod(member: GuildMember): VoiceChannelConnectionPeriod {
        return VoiceChannelConnectionPeriod(
            channelId = channel.id,
            memberId = member.id,
            connectionStart = LocalDateTime.now(),
            connectionEnd =  LocalDateTime.now()
        )
    }

    @Test
    fun getChannelConnectionStat() = runBlocking {
        val periodsA = flowOf(mockkPeriod(memberA), mockkPeriod(memberA))
        val periodsB = flowOf(mockkPeriod(memberB), mockkPeriod(memberB), mockkPeriod(memberB))
        coEvery {
            connectionsRepo.getAllByChannelIdAndConnectionStartAfterAndConnectionEndBefore(
                channel.id,
                any(),
                any()
            )
        } returns flow {
            emitAll(periodsA)
            emitAll(periodsB)
        }
        val resultMap = service.getChannelConnectionStat(channel)
        Assertions.assertArrayEquals(periodsA.toList().toTypedArray(), resultMap[memberA.id]?.toTypedArray())
        Assertions.assertArrayEquals(periodsB.toList().toTypedArray(), resultMap[memberB.id]?.toTypedArray())
    }
}
