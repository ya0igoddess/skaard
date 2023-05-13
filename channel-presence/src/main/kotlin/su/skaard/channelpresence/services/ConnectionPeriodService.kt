package su.skaard.channelpresence.services

import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service
import su.skaard.core.entities.discord.GuildMember
import su.skaard.channelpresence.model.entities.VoiceChannelConnectionPeriod
import su.skaard.channelpresence.repositories.VoiceChannelConnectionPeriodRepository
import su.skaard.core.entities.discord.DiscordChannel
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.stream.Collectors

@Service
class ConnectionPeriodService(
    val connectionPeriodRepository: VoiceChannelConnectionPeriodRepository
) {
    suspend fun getChannelConnectionStat(
        channel: DiscordChannel,
        date: LocalDate = LocalDateTime.now().toLocalDate()
    ): Map<Long, List<VoiceChannelConnectionPeriod>> {
        val connections = connectionPeriodRepository.getAllByChannelIdAndConnectionStartAfterAndConnectionEndBefore(
            channelId = channel.id,
            connectionStart = date.atStartOfDay(),
            connectionEnd = date.plusDays(1).atStartOfDay()
        )
        return  connections.toList().groupBy { it.memberId }
            .map { (k,v) -> k to v.sortedBy { it.connectionStart } }
            .toMap()
    }
}
