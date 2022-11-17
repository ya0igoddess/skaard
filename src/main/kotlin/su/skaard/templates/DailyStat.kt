package su.skaard.templates

import kotlinx.html.*
import su.skaard.model.discord.GuildMember
import su.skaard.model.discord.VoiceChannelConnectionPeriod
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime


private class ActivityPeriod(val activityType: ActivityType,
                     val beginTime: LocalDateTime,
                     val endTime: LocalDateTime)
{
    enum class ActivityType { Active, Inactive }
    val duration: Long = Duration.between(beginTime, endTime).toMinutes()
}

/**
 * Transforms the list of connection periods to list of activity periods and inactivity periods
 * between them.
 */
private fun createActivityPeriodList(periods: List<VoiceChannelConnectionPeriod>, date: LocalDate) : List<ActivityPeriod> {
    val result = ArrayList<ActivityPeriod>()
    var lastPeriodEnd = date.atStartOfDay()
    val dayEnd = LocalDate.now().plusDays(1).atStartOfDay()
    for (period in periods) {
        result.add(ActivityPeriod(
            ActivityPeriod.ActivityType.Inactive,
            lastPeriodEnd,
            period.connectionStart))
        result.add(ActivityPeriod(
            ActivityPeriod.ActivityType.Active,
                period.connectionStart,
                period.connectionEnd))
        lastPeriodEnd = period.connectionEnd
    }
    result.add(
        ActivityPeriod(
        ActivityPeriod.ActivityType.Inactive,
            lastPeriodEnd,
            dayEnd)
    )
    return result
}

private fun createMemberActivityLine(list: List<ActivityPeriod>): DIV.()->Unit = {
    val minutesInDay = 60*24
    list.forEach {
        div {
            style = "width:${(it.duration/minutesInDay.toFloat()) * 100}%;"
            classes = setOf("activity", if (it.activityType == ActivityPeriod.ActivityType.Active) {
                "activity-active"
            } else {
                "activity-inactive"
            })
        }
    }
}


private fun createMemberDailyStat(member: GuildMember,
                          periods: List<VoiceChannelConnectionPeriod>,
                          date: LocalDate
) : DIV.() -> Unit = {
    div { +member.discordUser.name }
    val periodList = createActivityPeriodList(periods, date)
        div("activity-line", createMemberActivityLine(periodList))
}

fun createActivityStat(stat: Map<GuildMember, List<VoiceChannelConnectionPeriod>>, date: LocalDate) : DIV.() -> Unit = {
    div("daily-stat") {
        div("activity-row",block = createDailyStatHeader())
        stat.entries.forEach {
            div("activity-row",block = createMemberDailyStat(it.key, it.value, date))
        }
    }
}

fun createDailyStatHeader() : DIV.() -> Unit = {
    div{}
    div("stat-header-time") {
        for (hour in 0..23) div { +"${hour/10}${hour%10}:00" }
    }
}
