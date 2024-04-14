package com.example.moneto.data

sealed class TransactionType(val name: String, val target: String) {
    object Income : TransactionType("Income", "Income")
    object Expense : TransactionType("Expense", "Expense")
}

fun String.toTransactionType(): TransactionType {
    return when(this) {
        "Income" -> TransactionType.Income
        "Expense" -> TransactionType.Expense
        else -> TransactionType.Expense
    }
}