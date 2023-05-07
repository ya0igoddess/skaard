package su.skaard.core.repositories.discord

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import su.skaard.core.entities.discord.DiscordUser
import su.skaard.core.entities.discord.Guild
import su.skaard.core.entities.discord.GuildMember

@Repository
interface GuildMemberRepository : JpaRepository<GuildMember, Long> {
    // override fun findById(id: Long): Optional<GuildMember>
    fun searchById(id: Long): GuildMember?
    fun getByGuildAndDiscordUser(guild: Guild, discordUser: DiscordUser): GuildMember?

    fun getByGuildIdAndDiscordUserId(guild: Long, discordUserId: Long): GuildMember?
    override fun <S : GuildMember> save(entity: S): S
}
