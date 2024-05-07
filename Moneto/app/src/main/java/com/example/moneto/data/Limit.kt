package com.example.moneto.data

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
/**
 * Trieda reprezentujúca limit výdavkov v aplikácii Moneto.
 * Umožňuje definovať denné a mesačné limity výdavkov, ktoré pomáhajú užívateľom spravovať ich finančné zdroje.
 * Táto trieda je objektom Realm, ktorý umožňuje uchovávanie a načítanie limitov z lokálnej databázy.
 */
class Limit() : RealmObject {
    @PrimaryKey
    var id: Int = 1
    
    var dailyLimit: Double = 0.0
    var monthlyLimit: Double = 0.0
    /**
     * Konštruktor pre vytvorenie limitu s konkrétnymi hodnotami.
     * @param id Identifikátor limitu, ktorý by mal byť jedinečný.
     * @param dailyLimit Denný limit výdavkov, vyjadrený ako suma peňazí.
     * @param monthlyLimit Mesačný limit výdavkov, vyjadrený ako suma peňazí.
     */
    constructor(
        id: Int,
        dailyLimit: Double,
        monthlyLimit: Double
    ) : this() {
        this.id = id
        this.dailyLimit = dailyLimit
        this.monthlyLimit = monthlyLimit
    }

}