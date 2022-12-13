package su.skaard.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import su.skaard.repositories.discord.ChannelRepository
import su.skaard.service.ConnectionPeriodService
import su.skaard.templates.createActivityStat
import su.skaard.templates.createCustomHTML
import java.time.LocalDate

@Controller
class DailyStatController @Autowired constructor(
    val connectionPeriodService: ConnectionPeriodService,
    val channelRepository: ChannelRepository
) {
    @GetMapping("/daily-stat")
    @ResponseBody
    fun getDailyStat(
        @RequestParam channelId: ULong,
        @RequestParam(required = false)
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        date: LocalDate?
    ): String {
        val localDate = date ?: LocalDate.now()
        val channel = channelRepository.searchById(channelId.toLong()) ?: throw IllegalArgumentException("NonExistingChannel")
        val stat = connectionPeriodService.getChannelConnectionStat(channel, localDate)
        return createCustomHTML(block = createActivityStat(stat, localDate))
    }
}
