package com.example.moneto.data

sealed class TransactionType(val name: String) {
    data object Income : TransactionType("Income")
    data object Expense : TransactionType("Expense")
}

fun String.toTransactionType(): TransactionType {
    return when(this) {
        "Income" -> TransactionType.Income
        "Expense" -> TransactionType.Expense
        else -> TransactionType.Expense
    }
}