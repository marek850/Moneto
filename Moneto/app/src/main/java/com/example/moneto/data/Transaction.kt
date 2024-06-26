package com.example.moneto.data


import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId
import java.time.LocalDateTime

/**
 * Trieda reprezentujúca finančnú transakciu v aplikácii Moneto.
 * Každá transakcia obsahuje popis, sumu, typ, dátum a môže byť priradená kategórii.
 */

class Transaction() : RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId.invoke()
    var description: String = ""
    var amount: Double = 0.0
    private var _type: String = "Expense"
    val type: TransactionType get() { return _type.toTransactionType() }
    private var _date: String = LocalDateTime.now().toString()
    val date: LocalDateTime get() { return LocalDateTime.parse(_date) }

    private var category: Category? = null
    val categ: Category? get() {return category}

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
/**
 * Reprezentuje súčet transakcií za jeden deň.
 */
data class OneDaySumTransactions(
    val transactions: MutableList<Transaction>,
    var total: Double,)
/**
 * Reprezentuje súčet transakcií za jednu hodinu.
 */
data class OneHourSumTransactions(
    val transactions: MutableList<Transaction>,
    var total: Double
)
/**
 * Zoskupuje transakcie podľa hodiny dňa.
 * @return Mapa z hodiny dňa na príslušné transakcie a ich súčet.
 */
fun List<Transaction>.groupedByHourOfDay(): Map<Int, OneHourSumTransactions> {
    val dataMap: MutableMap<Int, OneHourSumTransactions> = mutableMapOf()

    this.forEach { transaction ->
        val hourOfDay = transaction.date.toLocalTime().hour

        val oneHourTransactions = dataMap.getOrPut(hourOfDay) {
            OneHourSumTransactions(
                transactions = mutableListOf(),
                total = 0.0
            )
        }
        oneHourTransactions.transactions.add(transaction)
        if (transaction.type == TransactionType.Income)
            oneHourTransactions.total += transaction.amount
        else
            oneHourTransactions.total -= transaction.amount
    }

    dataMap.values.forEach { oneHourSumTransactions ->
        oneHourSumTransactions.transactions.sortBy { it.date }
    }

    return dataMap.toSortedMap()
}
/**
 * Zoskupuje transakcie podľa mesiaca.
 * @return Mapa z názvu mesiaca na príslušné transakcie a ich súčet.
 */
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
            if (transaction.type == TransactionType.Income)
                dataMap[month.name]!!.total = dataMap[month.name]!!.total.plus(transaction.amount)
            else
                dataMap[month.name]!!.total = dataMap[month.name]!!.total.minus(transaction.amount)
        }

        return dataMap.toSortedMap(compareByDescending { it })
    }
/**
 * Zoskupuje transakcie podľa dňa v týždni.
 * @return Mapa z názvu dňa v týždni na príslušné transakcie a ich súčet.
 */
fun List<Transaction>.groupedByDayOfWeek(): Map<String, OneDaySumTransactions> {
    val dataMap: MutableMap<String, OneDaySumTransactions> = mutableMapOf()

    this.forEach { transaction ->
        val dayOfWeek = transaction.date.toLocalDate().dayOfWeek

        if (dataMap[dayOfWeek.name] == null) {
            dataMap[dayOfWeek.name] = OneDaySumTransactions(
                mutableListOf(),
                total = 0.0
            )
        }

        dataMap[dayOfWeek.name]!!.transactions.add(transaction)
        if (transaction.type == TransactionType.Income)
            dataMap[dayOfWeek.name]!!.total = dataMap[dayOfWeek.name]!!.total.plus(transaction.amount)
        else
            dataMap[dayOfWeek.name]!!.total = dataMap[dayOfWeek.name]!!.total.minus(transaction.amount)
    }

    return dataMap.toSortedMap(compareByDescending { it })
}
/**
* Zoskupuje transakcie podľa dňa v mesiaci.
* @return Mapa z dňa v mesiaci na príslušné transakcie a ich súčet.
*/
fun List<Transaction>.groupedByDayOfMonth(): Map<Int, OneDaySumTransactions> {
        val dataMap: MutableMap<Int, OneDaySumTransactions> = mutableMapOf()

        this.forEach { transaction ->
            val dayOfMonth = transaction.date.toLocalDate().dayOfMonth

            if (dataMap[dayOfMonth] == null) {
                dataMap[dayOfMonth] = OneDaySumTransactions(
                    transactions = mutableListOf(),
                    total = 0.0
                )
            }

            dataMap[dayOfMonth]!!.transactions.add(transaction)
            if (transaction.type == TransactionType.Income)
                dataMap[dayOfMonth]!!.total = dataMap[dayOfMonth]!!.total.plus(transaction.amount)
            else
                dataMap[dayOfMonth]!!.total = dataMap[dayOfMonth]!!.total.minus(transaction.amount)

        }

        return dataMap.toSortedMap(compareByDescending { it })
}