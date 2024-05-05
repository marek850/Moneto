package com.example.moneto

sealed class Screens (val screen: String) {
    data object Home: Screens("home")
    data object Statistics: Screens("statistics")
    data object Settings: Screens("settings")
    data object Info: Screens("info")
    data object AddTransaction: Screens("addExpense")
    data object Categories: Screens("categories")
    data object Register: Screens("register")
    data object Login: Screens("login")
    data object Currencies: Screens("currencies")
    data object Limits: Screens("limits")

}