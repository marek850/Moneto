package com.example.moneto.data

sealed class Curr (val code: String, val symbol: String) {
    data object UnitedStatesDollar : Curr("USD", "$")
    data object Euro : Curr("EUR", "€")
    data object AustralianDollar : Curr("AUD", "AU$")
    data object JapaneseYen : Curr("JPY", "¥")
}

