package su.skaard.channelpresence.controllers

import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import su.skaard.core.entities.discord.DiscordUser
import su.skaard.core.repositories.discord.ChannelRepository
import su.skaard.channelpresence.services.ConnectionPeriodService
import su.skaard.core.security.SecurityService
import su.skaard.channelpresence.templates.createActivityStat
import su.skaard.core.templates.createCustomHTML
import su.skaard.core.utils.asDiscordUser
import java.nio.file.AccessDeniedException
import java.security.Principal
import java.time.LocalDate

@Controller
class DailyStatController @Autowired constructor(
    val connectionPeriodService: ConnectionPeriodService,
    val channelRepository: ChannelRepository,
    val securityService: SecurityService
) {
    @GetMapping("/daily-stat")
    @ResponseBody
    fun getDailyStat(
        @RequestParam channelId: Long,
        @RequestParam(required = false)
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        date: LocalDate?,
        principal: Principal
    ): String {
//        return runBlocking {
//            val localDate = date ?: LocalDate.now()
//            val channel =
//                channelRepository.findById(channelId) ?: throw IllegalArgumentException("NonExistingChannel")
//            val user: DiscordUser = principal.asDiscordUser() ?: throw IllegalStateException("Principal doesn't get")
//            if (!securityService.isUserMemberOfChannel(user, channel)) {
//                throw AccessDeniedException("The current user doesn't have rights to this channel")
//            }
//            val stat = connectionPeriodService.getChannelConnectionStat(channel, localDate)
//            return@runBlocking createCustomHTML(block = createActivityStat(stat, localDate))
//        }
        return ""
    }

}
