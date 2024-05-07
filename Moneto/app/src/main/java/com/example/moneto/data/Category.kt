package com.example.moneto.data

//import io.realm.kotlin.types.ObjectId
import io.realm.kotlin.types.RealmObject
//import io.realm.kotlin.types.RealmObject
import org.mongodb.kbson.ObjectId
/**
 * Trieda reprezentujúca kategóriu v aplikácii Moneto.
 * Každá kategória má svoj jedinečný identifikátor a názov, ktoré pomáhajú organizovať a kategorizovať transakcie.
 * Táto trieda je objektom Realm, čo umožňuje jej uchovávanie a načítanie z lokálnej databázy.
 */
class Category() : RealmObject {
    @io.realm.kotlin.types.annotations.PrimaryKey
    var _id: ObjectId = ObjectId.invoke()
    var name: String = ""
    /**
     * Konštruktor pre vytvorenie kategórie s názvom.
     * @param name Názov kategórie, ktorý by mal byť jasný a výstižný, aby užívateľom poskytol účinnú orientáciu.
     */
    constructor(
        name: String,
    ) : this() {
        this.name = name
    }
}