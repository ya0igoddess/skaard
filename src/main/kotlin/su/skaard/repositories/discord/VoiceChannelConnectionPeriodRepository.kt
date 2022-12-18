package su.skaard.repositories.discord

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import su.skaard.model.discord.Channel
import su.skaard.model.discord.VoiceChannelConnectionPeriod
import java.time.LocalDateTime

@Repository
interface VoiceChannelConnectionPeriodRepository : JpaRepository<VoiceChannelConnectionPeriod, Long> {
    // override fun findById(id: Long): Optional<VoiceChannelConnectionPeriod>
    fun searchById(id: Long): VoiceChannelConnectionPeriod?
    fun getAllByChannel(channel: Channel): List<VoiceChannelConnectionPeriod>
    fun getAllByChannelAndConnectionStartAfterAndConnectionEndBefore(
        channel: Channel,
        connectionStart: LocalDateTime,
        connectionEnd: LocalDateTime
    ): List<VoiceChannelConnectionPeriod>
    override fun <S : VoiceChannelConnectionPeriod> save(entity: S): S
}
