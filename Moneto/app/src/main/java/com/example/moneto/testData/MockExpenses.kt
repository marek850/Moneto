package com.example.moneto.testData

import com.example.moneto.data.Category
import com.example.moneto.data.TransactionType
import io.github.serpro69.kfaker.Faker


val faker = Faker()

val mockCategories = listOf(
    Category(
        "Bills"
    ),
    Category(
        "Subscriptions"
    ),
    Category(
        "Take out"
    ),
    Category(
        "Hobbies"
    ),
)

val mockTransactionTypes = listOf(
    TransactionType.Income,
    TransactionType.Expense
)/*
val mockExpenses: List<Transaction> = List(30) {
    Transaction(
        amount = faker.random.nextInt(min = 1, max = 999)
            .toDouble() + faker.random.nextDouble(),
        date = LocalDateTime.now().minus(
            faker.random.nextInt(min = 300, max = 345600).toLong(),
            ChronoUnit.SECONDS
        ),

        description = faker.australia.animals(),
        category = faker.random.randomValue(mockCategories),
        transactionType = faker.random.randomValue(mockTransactionTypes)
    )
}*/