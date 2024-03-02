package com.example.moneto

import android.os.Bundle
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.moneto.screens.ExpensesScreen
import com.example.moneto.screens.HomeScreen
import com.example.moneto.screens.InfoScreen
import com.example.moneto.screens.SettingsScreen
import com.example.moneto.ui.theme.Login
import com.example.moneto.ui.theme.MonetoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MonetoTheme {
                // A surface container using the 'background' color from the theme
                BottomNavBar()
                //SettingsScreen()
            }
        }
    }
}

@Composable
fun BottomNavBar() {
    val navigationController = rememberNavController()
    val context = LocalContext.current.applicationContext
    val selected = remember {
        mutableStateOf(Icons.Default.Home)
    }
    Scaffold(bottomBar = {BottomAppBar(containerColor = Color(0xFF201a30)) {
        IconButton(
            onClick = { /*TODO*/
                selected.value = Icons.Default.Home
                navigationController.navigate(Screens.Home.screen){
                    popUpTo(0)
                 }
            }, modifier = Modifier.weight(1f)) {
            Icon(Icons.Default.Home, contentDescription = null, modifier = Modifier.size(26.dp), tint = if (selected.value == Icons.Default.Home) Color.White else Color.LightGray)
        }
        IconButton(
            onClick = { /*TODO*/
                selected.value = Icons.Default.List
                navigationController.navigate(Screens.Expenses.screen){
                    popUpTo(0)
                }
            }, modifier = Modifier.weight(1f)) {
            Icon(Icons.Default.List, contentDescription = null, modifier = Modifier.size(26.dp), tint = if (selected.value == Icons.Default.List) Color.White else Color.LightGray)
        }
        Button(
            onClick = { },
            modifier = Modifier
                .weight(1f)// Diameter of the circular button
                .clip(CircleShape), // Clip the button to a circle shape
            colors = ButtonDefaults.buttonColors(containerColor = Login, contentColor = Color.White)
        ) {
            Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier
                .fillMaxSize() // Set the icon size
                )// Add padding if necessary)
        }
        IconButton(
            onClick = { /*TODO*/
                selected.value = Icons.Default.Settings
                navigationController.navigate(Screens.Settings.screen){
                    popUpTo(0)
                }
            }, modifier = Modifier.weight(1f)) {
            Icon(Icons.Default.Settings, contentDescription = null, modifier = Modifier.size(26.dp), tint = if (selected.value == Icons.Default.Settings) Color.White else Color.LightGray)
        }
        IconButton(
            onClick = { /*TODO*/
                selected.value = Icons.Default.Info
                navigationController.navigate(Screens.Info.screen){
                    popUpTo(0)
                }
            }, modifier = Modifier.weight(1f)) {
            Icon(Icons.Default.Info, contentDescription = null, modifier = Modifier.size(26.dp), tint = if (selected.value == Icons.Default.Info) Color.White else Color.LightGray)
        }
    }}) {paddingValues ->
        NavHost(navController = navigationController, startDestination = Screens.Home.screen, modifier = Modifier.padding(paddingValues) ){
            composable(Screens.Home.screen){ HomeScreen() }
            composable(Screens.Settings.screen){ SettingsScreen() }
            composable(Screens.Info.screen){ InfoScreen() }
            composable(Screens.Expenses.screen){ ExpensesScreen() }
        }

    }

}