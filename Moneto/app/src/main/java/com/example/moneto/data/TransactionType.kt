package com.example.moneto.data

sealed class TransactionType(val name: String) {
    object Income : TransactionType("Income")
    object Expense : TransactionType("Expense")
}

fun String.toTransactionType(): TransactionType {
    return when(this) {
        "Income" -> TransactionType.Income
        "Expense" -> TransactionType.Expense
        else -> TransactionType.Expense
    }
}