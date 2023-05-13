package su.skaard.channelpresence.repositories

import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import su.skaard.channelpresence.model.entities.VoiceChannelConnectionPeriod
import java.time.LocalDateTime

@Repository
interface VoiceChannelConnectionPeriodRepository : CoroutineCrudRepository<VoiceChannelConnectionPeriod, Long> {
    suspend fun getAllByChannelId(channelId: Long): Flow<VoiceChannelConnectionPeriod>
    suspend fun getAllByChannelIdAndConnectionStartAfterAndConnectionEndBefore(
        channelId: Long,
        connectionStart: LocalDateTime,
        connectionEnd: LocalDateTime
    ): Flow<VoiceChannelConnectionPeriod>
}
