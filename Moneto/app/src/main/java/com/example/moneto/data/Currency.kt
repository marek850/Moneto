package com.example.moneto.data

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class Currency() : RealmObject {
    @PrimaryKey
    var id: Int = 1
    var code: String = "USD"
    var symbol: String = "$"
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