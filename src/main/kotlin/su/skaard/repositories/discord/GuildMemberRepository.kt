package su.skaard.repositories.discord

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import su.skaard.model.discord.DiscordUser
import su.skaard.model.discord.Guild
import su.skaard.model.discord.GuildMember
import java.util.*

@Repository
interface GuildMemberRepository: JpaRepository<GuildMember, Long> {
    override fun findById(id: Long): Optional<GuildMember>
    fun findByGuildAndDiscordUser(guild: Guild, discordUser: DiscordUser): Optional<GuildMember>
    override fun <S : GuildMember> save(entity: S): S
}