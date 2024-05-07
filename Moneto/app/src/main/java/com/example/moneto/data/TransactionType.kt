package com.example.moneto.data
/**
 * Reprezentuje rôzne typy finančných transakcií.
 * Táto uzavretá trieda umožňuje dobre definovaný súbor typov transakcií, čo zvyšuje bezpečnosť typov a zaručuje,
 * že v celej aplikácii sú používané len preddefinované typy.
 */
sealed class TransactionType(val name: String) {
    data object Income : TransactionType("Income")
    data object Expense : TransactionType("Expense")
    data object All : TransactionType("All")
}
/**
 * Konvertuje reťazec na príslušný objekt `TransactionType`.
 * Ak reťazec nezodpovedá žiadnemu známemu typu, predvolene nastaví `TransactionType.Expense`.
 *
 * @return Zodpovedajúci `TransactionType` alebo `TransactionType.Expense`, ak sa zhoda nenájde.
 */
fun String.toTransactionType(): TransactionType {
    return when(this) {
        "Income" -> TransactionType.Income
        "Expense" -> TransactionType.Expense
        "All" -> TransactionType.All
        else -> TransactionType.Expense
    }
}

