package com.example.moneto.data

import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

val config = RealmConfiguration.create(schema = setOf(Category::class, Transaction::class))
val monetoDb: Realm = Realm.open(config)