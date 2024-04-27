package com.example.moneto.data

sealed class TimeRange (val name: String) {
    data object Day : TimeRange("Today")
    data object Week : TimeRange("This week")
    data object Month : TimeRange("This month")
    data object Year : TimeRange("This year")
}

