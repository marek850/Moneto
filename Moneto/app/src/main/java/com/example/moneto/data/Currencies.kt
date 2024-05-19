package com.example.moneto.data
/**
 * Uzavretá trieda definujúca rôzne meny používané v aplikácii Moneto.
 * Každý objekt tejto triedy predstavuje jednu menu a obsahuje jej medzinárodný kód a symbol.
 * Táto štruktúra zaručuje, že len preddefinované meny môžu byť použité v celej aplikácii, čím zvyšuje bezpečnosť typov
 * a predvídateľnosť operácií súvisiacich s menami.
 */
sealed class Curr (val code: String, val symbol: String) {
    /**
     * Predstavuje Americký dolár s kódom "USD" a symbolom "$".
     */
    data object UnitedStatesDollar : Curr("USD", "$")
    /**
     * Predstavuje Euro s kódom "EUR" a symbolom "€".
     */
    data object Euro : Curr("EUR", "€")
    /**
     * Predstavuje Austrálsky dolár s kódom "AUD" a symbolom "AU$".
     */
    data object AustralianDollar : Curr("AUD", "AU$")
    /**
     * Predstavuje Japonský jen s kódom "JPY" a symbolom "¥".
     */
    data object JapaneseYen : Curr("JPY", "¥")
}

