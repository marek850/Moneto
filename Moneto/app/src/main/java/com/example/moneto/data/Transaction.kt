package com.example.moneto.data


import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId
import java.time.LocalDateTime

/*
@Entity
data class Transaction(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "amount") val amount: Double?,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "transactionType") val transactionType: TransactionType,
    @ColumnInfo(name = "date") val date: DateTime,
    @ColumnInfo(name = "category") val category: Category
)*/
class Transaction() : RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId.invoke()
    var description: String = ""
    var amount: Double = 0.0
    private var _type: String = "Expense"
    val type: TransactionType get() { return _type.toTransactionType() }
    private var _date: String = LocalDateTime.now().toString()
    val date: LocalDateTime get() { return LocalDateTime.parse(_date) }
    var category: Category? = null

    constructor(
        description: String,
        amount: Double,
        type: TransactionType,
        date: LocalDateTime,
        category: Category,
    ) : this() {
        this.description = description
        this.amount = amount
        this._type = type.name
        this._date = date.toString()
        this.category = category
    }
}
    data class OneDaySumTransactions(
        val transactions: MutableList<Transaction>,
        var total: Double,)

    fun List<Transaction>.groupedByMonth(): Map<String, OneDaySumTransactions> {
        val dataMap: MutableMap<String, OneDaySumTransactions> = mutableMapOf()

        this.forEach { transaction ->
            val month = transaction.date.toLocalDate().month

            if (dataMap[month.name] == null) {
                dataMap[month.name] = OneDaySumTransactions(
                    transactions = mutableListOf(),
                    total = 0.0
                )
            }

            dataMap[month.name]!!.transactions.add(transaction)
            dataMap[month.name]!!.total = dataMap[month.name]!!.total.plus(transaction.amount)
        }

        return dataMap.toSortedMap(compareByDescending { it })
    }
