package com.example.moneto.utils

import com.example.moneto.data.TimeRange
import java.time.LocalDate
import java.time.YearMonth

data class DateRangeData(
    val start: LocalDate,
    val end: LocalDate,
    val daysInRange: Int
)

fun calculateDateRange(timeRange: TimeRange): DateRangeData {
    val today = LocalDate.now()
    lateinit var start: LocalDate
    lateinit var end: LocalDate
    var daysInRange = 7

    when (timeRange) {
        TimeRange.Day -> {
            start = LocalDate.now()
            end = start
        }
        TimeRange.Week -> {
            start =
                LocalDate.now().minusDays(today.dayOfWeek.value.toLong() - 1)
            end = start.plusDays(6)
            daysInRange = 7
        }
        TimeRange.Month -> {
            start =
                LocalDate.of(today.year, today.month, 1)
            val numberOfDays =
                YearMonth.of(start.year, start.month).lengthOfMonth()
            end = start.plusDays(numberOfDays.toLong())
            daysInRange = numberOfDays
        }
        TimeRange.Year -> {
            start = LocalDate.of(today.year, 1, 1)
            end = LocalDate.of(today.year, 12, 31)
            daysInRange = 365
        }
        else -> Unit
    }

    return DateRangeData(
        start = start,
        end = end,
        daysInRange = daysInRange
    )
}