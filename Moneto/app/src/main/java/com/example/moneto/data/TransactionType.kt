package com.example.moneto.data

sealed class TransactionType(val name: String) {
    data object Income : TransactionType("Income")
    data object Expense : TransactionType("Expense")
    data object All : TransactionType("All")
}

fun String.toTransactionType(): TransactionType {
    return when(this) {
        "Income" -> TransactionType.Income
        "Expense" -> TransactionType.Expense
        "All" -> TransactionType.All
        else -> TransactionType.Expense
    }
}