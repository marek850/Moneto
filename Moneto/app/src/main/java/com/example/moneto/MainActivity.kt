package com.example.moneto

/*
import com.example.moneto.screens.Categories
*/
import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.moneto.data.Category
import com.example.moneto.data.Curr
import com.example.moneto.data.Currency
import com.example.moneto.data.monetoDb
import com.example.moneto.notifications.scheduleChecks
import com.example.moneto.screens.AddTransaction
import com.example.moneto.screens.Categories
import com.example.moneto.screens.CurrenciesScreen
import com.example.moneto.screens.HomeScreen
import com.example.moneto.screens.InfoScreen
import com.example.moneto.screens.LimitSetScreen
import com.example.moneto.screens.RegisterScreen
import com.example.moneto.screens.SettingsScreen
import com.example.moneto.screens.StatisticScreen
import com.example.moneto.ui.theme.Background
import com.example.moneto.ui.theme.Login
import com.example.moneto.ui.theme.MonetoTheme
import io.realm.kotlin.ext.query
import io.sentry.compose.withSentryObservableEffect

class MainActivity :  ComponentActivity(){

    companion object{
        private const val NOTIFICATION_PERMISSION = 100
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val name = "Moneto Notifications"
        val descriptionText = "Moneto app notification channel"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel("CHANNEL_ID", name, importance).apply {
            description = descriptionText
        }

        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
        scheduleChecks(this)

        setContent {
            MonetoTheme {

                BottomNavBar()
            }
        }
        checkPermission(Manifest.permission.POST_NOTIFICATIONS, NOTIFICATION_PERMISSION)

    }
    private fun checkPermission(permission: String, requestCode: Int){
        if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
        }else   {
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == NOTIFICATION_PERMISSION){
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Notification permission granted", Toast.LENGTH_SHORT).show()
            } else  {
                Toast.makeText(this, "Notification permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
@Composable
fun BottomNavBar() {

    val navigationController = rememberNavController().withSentryObservableEffect()
    var showBottomBar by rememberSaveable { mutableStateOf(true) }
    val backStackEntry by navigationController.currentBackStackEntryAsState()

    LaunchedEffect(Unit) {
        monetoDb.write {
            val deletedCurrency = this.query<Currency>().find()
            if (deletedCurrency.isEmpty())
                this.copyToRealm(
                Currency(1, Curr.UnitedStatesDollar.code, Curr.UnitedStatesDollar.symbol)
                )
        }
        monetoDb.write {
            val categoriesToCreate = listOf("Sallary", "Restaurant", "Groceries", "Rent", "Drugs", "Car")
            val categories = this.query<Category>().find()
            val existingCategoryNames = categories.map { it.name }.toSet()

            categoriesToCreate.forEach { categoryName ->
                if (categoryName !in existingCategoryNames) {
                    val newCategory = Category(categoryName)
                    this.copyToRealm(newCategory)

                }
            }
        }

    }

    showBottomBar = when (backStackEntry?.destination?.route) {
        Screens.Categories.screen  -> false
        Screens.Currencies.screen -> false
        Screens.Limits.screen -> false
        else -> true
    }
    val selected = remember {
        mutableStateOf(Icons.Default.Home)
    }
    Scaffold(bottomBar = {if(showBottomBar) {

    BottomAppBar(containerColor = Background) {
        IconButton(
            onClick = {
                selected.value = Icons.Default.Home
                navigationController.navigate(Screens.Home.screen){
                    popUpTo(0)
                 }
            }, modifier = Modifier.weight(1f)) {
            Icon(Icons.Default.Home, contentDescription = null, modifier = Modifier.size(26.dp), tint = if (selected.value == Icons.Default.Home) Color.White else Color.LightGray)
        }
        IconButton(
            onClick = {
                selected.value = Icons.Default.List
                navigationController.navigate(Screens.Statistics.screen){
                    popUpTo(0)
                }
            }, modifier = Modifier.weight(1f)) {
            Icon(Icons.Default.List, contentDescription = null, modifier = Modifier.size(26.dp), tint = if (selected.value == Icons.Default.List) Color.White else Color.LightGray)
        }
        Button(
            onClick = {navigationController.navigate(Screens.AddExpense.screen){
                popUpTo(0)
            } },
            modifier = Modifier
                .weight(1f)
                .clip(CircleShape),
            colors = ButtonDefaults.buttonColors(containerColor = Login, contentColor = Color.White)
        ) {
            Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier
                .fillMaxSize()
                )
        }
        IconButton(
            onClick = {
                selected.value = Icons.Default.Settings
                navigationController.navigate(Screens.Settings.screen){
                    popUpTo(0)
                }
            }, modifier = Modifier.weight(1f)) {
            Icon(Icons.Default.Settings, contentDescription = null, modifier = Modifier.size(26.dp), tint = if (selected.value == Icons.Default.Settings) Color.White else Color.LightGray)
        }
        IconButton(
            onClick = {
                selected.value = Icons.Default.Info
                navigationController.navigate(Screens.Info.screen){
                    popUpTo(0)
                }
            }, modifier = Modifier.weight(1f)) {
            Icon(Icons.Default.Info, contentDescription = null, modifier = Modifier.size(26.dp), tint = if (selected.value == Icons.Default.Info) Color.White else Color.LightGray)
        }
    }}}) {paddingValues ->
        NavHost(navController = navigationController, startDestination = Screens.Home.screen, modifier = Modifier.padding(paddingValues) ){
            composable(Screens.Home.screen){ HomeScreen() }
            composable(Screens.Statistics.screen){ StatisticScreen() }
            composable(Screens.Settings.screen){ SettingsScreen(navigationController) }
            composable(Screens.Info.screen){ InfoScreen() }
            composable(Screens.AddExpense.screen){ AddTransaction() }
            composable(Screens.Register.screen){ RegisterScreen(navigationController) }
            composable(Screens.Categories.screen){Categories(navigationController)}
            composable(Screens.Currencies.screen){ CurrenciesScreen(navigationController) }
            composable(Screens.Limits.screen){ LimitSetScreen(navigationController) }
        }
    }
}