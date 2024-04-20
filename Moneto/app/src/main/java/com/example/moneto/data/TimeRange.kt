package com.example.moneto.data

sealed class TimeRange (val name: String) {
    object Day : TimeRange("Today")
    object Week : TimeRange("This week")
    object Month : TimeRange("This month")
    object Year : TimeRange("This year")
}

fun String.toTimeRange(): TimeRange {
    return when(this) {
        "Today" -> TimeRange.Day
        "This week" -> TimeRange.Week
        "This month" -> TimeRange.Month
        "This year" -> TimeRange.Year
        else -> TimeRange.Day
    }
}