package com.example.moneto

sealed class Screens (val screen: String) {
    data object Home: Screens("home")
    data object Expenses: Screens("expenses")
    data object Settings: Screens("settings")
    data object Info: Screens("info")
    data object AddExpense: Screens("addExpense")
    data object Categories: Screens("categories")
    data object Register: Screens("register")
    data object Login: Screens("login")
    data object Currencies: Screens("currencies")
    data object Limits: Screens("limits")

}