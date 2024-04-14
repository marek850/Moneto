package com.example.moneto.testData

import com.example.moneto.data.Category
import com.example.moneto.data.Expense
import io.github.serpro69.kfaker.Faker
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit


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

val mockExpenses: List<Expense> = List(30) {
    Expense(
        amount = faker.random.nextInt(min = 1, max = 999)
            .toDouble() + faker.random.nextDouble(),
        date = LocalDateTime.now().minus(
            faker.random.nextInt(min = 300, max = 345600).toLong(),
            ChronoUnit.SECONDS
        ),

        description = faker.australia.animals(),
        category = faker.random.randomValue(mockCategories)
    )
}