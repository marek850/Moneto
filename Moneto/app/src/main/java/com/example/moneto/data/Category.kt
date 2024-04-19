package com.example.moneto.data

//import io.realm.kotlin.types.ObjectId
import io.realm.kotlin.types.RealmObject
//import io.realm.kotlin.types.RealmObject
import org.mongodb.kbson.ObjectId

class Category() : RealmObject {
    @io.realm.kotlin.types.annotations.PrimaryKey
    var _id: ObjectId = ObjectId.invoke()
    var name: String = ""

    constructor(
        name: String,
    ) : this() {
        this.name = name
    }
}