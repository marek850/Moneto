package com.example.moneto.data

import java.time.LocalDateTime

data class Expense (
    val amount: Double,
    val date: LocalDateTime,
    val description: String,
    val category: Category
)
