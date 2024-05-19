package com.example.moneto

sealed class Screens (val screen: String) {
    /**
     * Represents the home screen of the application.
     */
    data object Home: Screens("home")
    /**
     * Represents the statistics screen where users can view various financial statistics.
     */
    data object Statistics: Screens("statistics")
    /**
     * Represents the settings screen for app configuration and preferences.
     */
    data object Settings: Screens("settings")
    /**
     * Represents the info screen providing information about the app.
     */
    data object Info: Screens("info")
    /**
     * Represents the screen for adding new transactions.
     */
    data object AddTransaction: Screens("addExpense")
    /**
     * Represents the categories management screen.
     */
    data object Categories: Screens("categories")
    /**
     * Represents the registration screen for new users.
     */
    data object Register: Screens("register")
    /**
     * Represents the login screen for user authentication.
     */
    data object Login: Screens("login")
    /**
     * Represents the currencies screen for managing different currencies.
     */
    data object Currencies: Screens("currencies")
    /**
     * Represents the limits screen for setting financial limits.
     */
    data object Limits: Screens("limits")

}