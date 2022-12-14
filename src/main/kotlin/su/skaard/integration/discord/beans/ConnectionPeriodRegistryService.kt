package su.skaard.integration.discord.beans

import dev.kord.common.entity.Snowflake
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import su.skaard.integration.discord.model.ClosedVoiceConnection
import su.skaard.integration.discord.model.OpenedVoiceConnection
import su.skaard.model.discord.VoiceChannelConnectionPeriod
import su.skaard.repositories.discord.*
import su.skaard.utils.IntegrationPersistenceException
import su.skaard.utils.getLogger

@Component
class ConnectionPeriodRegistryService @Autowired constructor(
    val userRepository: DiscordUserRepository,
    val guildMemberRepository: GuildMemberRepository,
    val channelRepository: ChannelRepository,
    val voiceChannelConnectionPeriodRepository: VoiceChannelConnectionPeriodRepository
) {
    private val logger = getLogger(ConnectionPeriodRegistryService::class.java)
    private val connections: MutableMap<String, OpenedVoiceConnection> = mutableMapOf()
    val openedConnections: Map<String, OpenedVoiceConnection> = connections

    fun openConnection(connectionId: String, channelId: Snowflake, userId: Snowflake) {
        logger.debug("Opening the connection $connectionId at channel $channelId")
        if (connections.containsKey(connectionId)) {
            throw IllegalStateException("Attempt to open the already opened connection (connection ID match)")
        }
        connections[connectionId] = OpenedVoiceConnection(channelId, userId)
    }

    fun closeConnection(connectionId: String) {
        logger.debug("Closing the connection $connectionId")
        val connection = connections.remove(connectionId)?.close()
        if (connection != null) saveConnection(connection) else return
    }

    private fun saveConnection(connection: ClosedVoiceConnection) = connection.let {
        val user = userRepository.searchById(it.userId.value.toLong()) ?: throw IntegrationPersistenceException()
        val channel = channelRepository.searchById(it.channelId.value.toLong()) ?: throw IntegrationPersistenceException()
        val guild = channel.guild
        val member = guildMemberRepository.getByGuildAndDiscordUser(guild, user) ?: throw IntegrationPersistenceException()
        voiceChannelConnectionPeriodRepository.save(
            VoiceChannelConnectionPeriod(
                id = 0UL,
                channel = channel,
                member = member,
                connectionStart = it.beginTime,
                connectionEnd = it.endTime
            )
        )
    }
}
