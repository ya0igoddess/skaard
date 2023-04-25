package su.skaard.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import su.skaard.core.repositories.discord.GuildsRepository
import su.skaard.service.ConnectionPeriodService
import su.skaard.core.security.SecurityService
import su.skaard.templates.createActivityStat
import su.skaard.core.templates.createCustomHTML
import su.skaard.core.utils.asDiscordUser
import java.nio.file.AccessDeniedException
import java.security.Principal
import java.time.LocalDate

/**
 * Returns channel activity stat like in
 * {@link DailyStatController DailyController} for a whole guild.
 */
@Controller
class DailyStatSummaryController @Autowired constructor(
    private val guildsRepository: GuildsRepository,
    private val periodService: ConnectionPeriodService,
    private val securityService: SecurityService
) {

    @GetMapping("/daily-stat/summary")
    @ResponseBody
    fun getDailyStatSummary(
        @RequestParam guildId: ULong,
        @RequestParam(required = false)
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        date: LocalDate?,
        principal: Principal
    ): String {
        val localDate = date ?: LocalDate.now()
        val guild = guildsRepository.searchById(guildId.toLong()) ?: throw IllegalArgumentException("NonExisting guild")
        val user = principal.asDiscordUser() ?: throw IllegalStateException("Principal association to user failed")
        if (!securityService.isUserMemberOfGuild(user, guild)) {
            throw AccessDeniedException("The user doesn't have right to the guild")
        }
        val stats = guild.channels.map(periodService::getChannelConnectionStat)
        return createCustomHTML {
            stats.forEach { createActivityStat(it, localDate)() }
        }
    }
}
