package su.skaard.core.services.repo

import dev.kord.core.entity.Member
import dev.kord.core.entity.User
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Service
import su.skaard.core.entities.discord.DiscordUser
import su.skaard.core.entities.discord.DiscordGuild
import su.skaard.core.entities.discord.GuildMember
import su.skaard.core.repositories.discord.GuildMemberRepository
import su.skaard.core.synchronization.services.ISyncRepoService

@Service
class GuildMemberService(
    private val repo: GuildMemberRepository,
    private val guildSync: ISyncRepoService<DiscordGuild, dev.kord.core.entity.Guild>,
    private val userSync: ISyncRepoService<DiscordUser, User>
) : IGuildMemberService {
    override suspend fun getByGuildAndUser(guild: DiscordGuild, user: DiscordUser): GuildMember? {
        return repo.getByGuildIdAndDiscordUserId(guild.id, user.id)
    }

    override suspend fun getById(id: Long): GuildMember? {
        return repo.findById(id)
    }

    override suspend fun deleteById(id: Long) {
        repo.deleteById(id)
    }

    override suspend fun save(entity: GuildMember): GuildMember {
        return repo.save(entity)
    }

    override suspend fun getByExternal(extEntity: Member): GuildMember? {
        val guildId = extEntity.guildId.value.toLong()
        val userId = extEntity.memberData.userId.value.toLong()
        return repo.getByGuildIdAndDiscordUserId(guildId, userId)
    }

    override suspend fun createFromExternal(extEntity: Member): GuildMember {
        val guild = runBlocking { guildSync.findOrCreateFromExt(extEntity.getGuild()) }
        val user = runBlocking { userSync.findOrCreateFromExt(extEntity.asUser()) }
        val member = GuildMember(
            discordUserId = user.id,
            guildId = guild.id
        )
        return repo.save(member)
    }
}