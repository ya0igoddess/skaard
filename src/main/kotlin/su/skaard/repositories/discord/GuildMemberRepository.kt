package su.skaard.repositories.discord

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import su.skaard.model.discord.DiscordUser
import su.skaard.model.discord.Guild
import su.skaard.model.discord.GuildMember

@Repository
interface GuildMemberRepository : JpaRepository<GuildMember, Long> {
    // override fun findById(id: Long): Optional<GuildMember>
    fun searchById(id: Long): GuildMember?
    fun getByGuildAndDiscordUser(guild: Guild, discordUser: DiscordUser): GuildMember?
    override fun <S : GuildMember> save(entity: S): S
}
