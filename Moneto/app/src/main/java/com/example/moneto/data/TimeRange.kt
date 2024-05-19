package com.example.moneto.data
/**
 * Uzavretá trieda definujúca rôzne časové rozsahy, ktoré možno používať v celej aplikácii Moneto
 * na filtrovanie alebo zoskupovanie údajov na základe časových období. Táto trieda zaručuje, že sa používajú len preddefinované
 * časové rozsahy, čím sa zvyšuje bezpečnosť typov a predvídateľnosť operácií súvisiacich s časom.
 */
sealed class TimeRange (val name: String) {
    /**
     * Predstavuje dátový objekt pre dnešný deň.
     */
    data object Day : TimeRange("Today")
    /**
     * Predstavuje dátový objekt pre tento týždeň.
     */
    data object Week : TimeRange("This week")
    /**
     * Predstavuje dátový objekt pre tento mesiac.
     */
    data object Month : TimeRange("This month")
    /**
     * Predstavuje dátový objekt pre tento rok.
     */
    data object Year : TimeRange("This year")
}

