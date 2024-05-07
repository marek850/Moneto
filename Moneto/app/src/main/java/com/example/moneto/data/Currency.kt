package com.example.moneto.data

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
/**
 * Trieda reprezentujúca menu v aplikácii Moneto.
 * Umožňuje definovať a uchovávať informácie o mene vrátane jej kódu a symbolu.
 * Táto trieda je objektom Realm, ktorý umožňuje uchovávanie a načítanie informácií o mene z lokálnej databázy.
 */
class Currency() : RealmObject {
    @PrimaryKey
    var id: Int = 1
    var code: String = "USD"
    var symbol: String = "$"
    /**
     * Konštruktor pre vytvorenie objektu meny s konkrétnymi hodnotami.
     * @param id Identifikátor meny, ktorý by mal byť jedinečný.
     * @param code Medzinárodný menový kód meny.
     * @param symbol Symbol meny.
     */
    constructor(
        id: Int,
        code: String,
        symbol: String
    ) : this() {
        this.id = id
        this.code = code
        this.symbol = symbol
    }
}