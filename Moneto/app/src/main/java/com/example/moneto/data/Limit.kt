package com.example.moneto.data

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class Limit() : RealmObject {
    @PrimaryKey
    var id: Int = 1
    
    var dailyLimit: Double = 0.0
    var monthlyLimit: Double = 0.0
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