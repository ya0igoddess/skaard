package su.skaard.repositories.discord

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import su.skaard.model.discord.VoiceChannelConnectionPeriod
import java.util.*

@Repository
interface VoiceChannelConnectionPeriodRepository: JpaRepository<VoiceChannelConnectionPeriod, Long> {
    override fun findById(id: Long): Optional<VoiceChannelConnectionPeriod>
    override fun <S : VoiceChannelConnectionPeriod> save(entity: S): S
}