package su.skaard.core.services.repo

import dev.kord.core.entity.Member
import dev.kord.core.entity.User
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Service
import su.skaard.core.entities.discord.DiscordUser
import su.skaard.core.entities.discord.Guild
import su.skaard.core.entities.discord.GuildMember
import su.skaard.core.repositories.discord.GuildMemberRepository
import su.skaard.core.synchronization.services.ISyncRepoService

@Service
class GuildMemberService(
    private val repo: GuildMemberRepository,
    private val guildSync: ISyncRepoService<Guild, dev.kord.core.entity.Guild>,
    private val userSync: ISyncRepoService<DiscordUser, User>
) : IGuildMemberService {
    override suspend fun getByGuildAndUser(guild: Guild, user: DiscordUser): GuildMember? {
        return repo.getByGuildAndDiscordUser(guild, user)
    }

    override suspend fun getById(id: ULong): GuildMember? {
        return repo.findById(id)
    }

    override suspend fun deleteById(id: ULong) {
        repo.deleteById(id)
    }

    override suspend fun save(entity: GuildMember): GuildMember {
        return repo.save(entity)
    }

    override suspend fun getByExternal(extEntity: Member): GuildMember? {
        val guildId = extEntity.guildId.value
        val userId = extEntity.memberData.userId.value
        return repo.getByGuildIdAndDiscordUserId(guildId.toLong(), userId.toLong())
    }

    override suspend fun createFromExternal(extEntity: Member): GuildMember {
        val guild = runBlocking { guildSync.findOrCreateFromExt(extEntity.getGuild()) }
        val user = runBlocking { userSync.findOrCreateFromExt(extEntity.asUser()) }
        val member = GuildMember(
            discordUser = user,
            guild = guild
        )
        return repo.save(member)
    }
}