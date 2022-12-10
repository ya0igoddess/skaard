package su.skaard.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import su.skaard.model.discord.Channel
import su.skaard.model.discord.GuildMember
import su.skaard.model.discord.VoiceChannelConnectionPeriod
import su.skaard.repositories.discord.VoiceChannelConnectionPeriodRepository
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.stream.Collectors

@Service
class ConnectionPeriodService @Autowired constructor(
    val connectionPeriodRepository: VoiceChannelConnectionPeriodRepository
) {
    fun getChannelConnectionStat(
        channel: Channel,
        date: LocalDate = LocalDateTime.now().toLocalDate()
    ): Map<GuildMember, List<VoiceChannelConnectionPeriod>> {
        val connections = connectionPeriodRepository.getAllByChannelAndConnectionStartAfterAndConnectionEndBefore(
            channel = channel,
            connectionStart = date.atStartOfDay(),
            connectionEnd = date.plusDays(1).atStartOfDay()
        )
        val periodsByMember = connections.stream()
            .collect(Collectors.groupingBy { it.member })
        periodsByMember.values.forEach { it.sortBy(VoiceChannelConnectionPeriod::connectionStart) }
        return periodsByMember
    }
}
