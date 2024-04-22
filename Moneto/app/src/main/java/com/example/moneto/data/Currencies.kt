package com.example.moneto.data

sealed class Curr (val code: String, val symbol: String) {
    object UnitedStatesDollar : Curr("USD", "$")
    object Euro : Curr("EUR", "€")
    object AustralianDollar : Curr("AUD", "AU$")
    object JappaneseYen : Curr("JPY", "¥")
}

fun String.toCurr(): Curr {
    return when(this) {
        "American dollar" -> Curr.UnitedStatesDollar
        "Euro " -> Curr.Euro
        "Australian dollar" -> Curr.AustralianDollar
        "Jappanese yen" -> Curr.JappaneseYen
        else -> Curr.Euro
    }
}