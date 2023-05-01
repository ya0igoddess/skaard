package su.skaard.channelpresence.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import su.skaard.core.entities.discord.Channel
import su.skaard.core.entities.discord.GuildMember
import su.skaard.channelpresence.model.entities.VoiceChannelConnectionPeriod
import su.skaard.channelpresence.repositories.VoiceChannelConnectionPeriodRepository
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.stream.Collectors

@Service
class ConnectionPeriodService(
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
