package su.skaard.channelpresence.services

import dev.kord.common.entity.Snowflake
import org.springframework.stereotype.Component
import su.skaard.core.repositories.discord.ChannelRepository
import su.skaard.core.repositories.discord.DiscordUserRepository
import su.skaard.core.repositories.discord.GuildMemberRepository
import su.skaard.channelpresence.model.periods.ClosedVoiceConnection
import su.skaard.channelpresence.model.periods.OpenedVoiceConnection
import su.skaard.channelpresence.model.entities.VoiceChannelConnectionPeriod
import su.skaard.channelpresence.repositories.VoiceChannelConnectionPeriodRepository
import su.skaard.core.utils.IntegrationPersistenceException
import su.skaard.core.utils.getLogger
import su.skaard.core.utils.lvalue

@Component
class ConnectionPeriodRegistryService(
    val guildMemberRepository: GuildMemberRepository,
    val channelRepository: ChannelRepository,
    val voiceChannelConnectionPeriodRepository: VoiceChannelConnectionPeriodRepository
) {
    private val logger = getLogger(ConnectionPeriodRegistryService::class.java)
    private val connections: MutableMap<String, OpenedVoiceConnection> = mutableMapOf()
    val openedConnections: Map<String, OpenedVoiceConnection> = connections

    suspend fun openConnection(connectionId: String, channelId: Snowflake, userId: Snowflake) {
        logger.debug("Opening the connection $connectionId at channel $channelId")
        if (connections.containsKey(connectionId)) {
            throw IllegalStateException("Attempt to open the already opened connection (connection ID match)")
        }
        connections[connectionId] = OpenedVoiceConnection(channelId, userId)
    }

    suspend fun closeConnection(connectionId: String) {
        logger.debug("Closing the connection $connectionId")
        val connection = connections.remove(connectionId)?.close()
        if (connection != null) saveConnection(connection) else return
    }

    private suspend fun saveConnection(connection: ClosedVoiceConnection) {
        val channel = requireNotNull(channelRepository.findById(connection.channelId.lvalue))
        val userId = connection.userId.lvalue
        val member = requireNotNull(guildMemberRepository.getByGuildIdAndDiscordUserId(channel.guildId, userId))
        voiceChannelConnectionPeriodRepository.save(
            VoiceChannelConnectionPeriod(
                channelId = channel.id,
                memberId = member.id,
                connectionStart = connection.beginTime,
                connectionEnd = connection.endTime
            )
        )
    }
}
