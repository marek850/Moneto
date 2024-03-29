package com.example.moneto

sealed class Screens (val screen: String) {
    data object Home: Screens("home")
    data object Expenses: Screens("expenses")
    data object Settings: Screens("settings")
    data object Info: Screens("info")
    data object AddExpense: Screens("addExpense")
}