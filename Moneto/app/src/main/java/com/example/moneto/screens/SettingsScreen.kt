package com.example.moneto.screens
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.moneto.Screens
import com.example.moneto.ui.theme.Background
import com.example.moneto.ui.theme.LightPurple
import com.example.moneto.ui.theme.Purple80
/**
 * Composable funkcia, ktorá zobrazuje obrazovku nastavení aplikácie Moneto.
 * Táto obrazovka je rozdelená na dve sekcie, všeobecné nastavenia a nastavenia súvisiace s rozpočtom,
 * pričom každá z nich umožňuje navigáciu na detailnejšie stránky nastavení.
 *
 * @param navController NavController, ktorý zabezpečuje navigáciu v aplikácii, používaný na navigáciu na rôzne obrazovky nastavení.
 */
@Composable
fun SettingsScreen(navController: NavController) {
    Column(modifier = Modifier.background(Background).fillMaxHeight()) {
        HeaderText()
        GeneralSettings(navController)
        BudgetSettings(navController)
    }
}
/**
 * Composable funkcia, ktorá zobrazuje nastavenia rozpočtu na obrazovke nastavení.
 * Poskytuje možnosti navigácie na správu mien a limitov.
 *
 * @param navController NavController používaný na navigáciu na príslušné obrazovky nastavení.
 */
@Composable
fun BudgetSettings(navController: NavController) {
    Column(modifier = Modifier
        .padding(horizontal = 14.dp)
        .padding(top = 10.dp)) {
        Text(
            text = "Budget", fontFamily = FontFamily.Serif,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        GeneralSettingsItem(icon = Icons.Default.AccountBox, mainText = "Currency", onClick = {navController.navigate(Screens.Currencies.screen)} )
        GeneralSettingsItem(icon = Icons.Default.Build, mainText = "Limit", onClick = {navController.navigate(Screens.Limits.screen)} )
    }
}
/**
 * Composable funkcia, ktorá zobrazuje možnosti všeobecných nastavení na obrazovke nastavení.
 * Zahŕňa navigáciu na obrazovku nastavení kategórií.
 *
 * @param navController NavController používaný na navigáciu v aplikácii.
 */
@Composable
fun GeneralSettings(navController: NavController) {
    Column(modifier = Modifier
        .padding(horizontal = 14.dp)
        .padding(top = 10.dp)) {
        Text(
            text = "General", fontFamily = FontFamily.Serif,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp)
            )
        GeneralSettingsItem(icon = Icons.Default.Menu, mainText = "Categories", onClick = {navController.navigate(Screens.Categories.screen)}
       )
    }
}
/**
 * Opakovane použiteľná composable funkcia, ktorá reprezentuje jednotlivú položku nastavenia v rámci obrazovky nastavení.
 * Táto kompozitná funkcia zobrazuje ikonu a text a poskytuje funkčnosť kliknutia na navigáciu na základe poskytnutého spätného volania.
 *
 * @param icon Ikonka, ktorá sa má zobraziť vedľa textu nastavenia.
 * @param mainText Textový popis nastavenia.
 * @param onClick Lambda funkcia, ktorá sa spustí pri kliknutí na položku.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GeneralSettingsItem(
    icon: ImageVector,
    mainText:String,
    onClick:()->Unit
) {
    Card(onClick = onClick,
            colors = CardDefaults.cardColors(
            containerColor = LightPurple
            ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        ), modifier = Modifier
            .padding(bottom = 8.dp)
            .fillMaxWidth()
    ) {
        Row(modifier = Modifier.padding(vertical = 10.dp, horizontal = 14.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
        , horizontalArrangement = Arrangement.SpaceBetween)
        {
            Row(verticalAlignment = Alignment.CenterVertically) {


                Box(modifier = Modifier
                    .size(34.dp)
                    .clip(shape = RoundedCornerShape(10.dp))
                    .background(
                        LightPurple
                    ))
                {
                    Icon(icon, contentDescription = null,
                        tint = Color.LightGray,
                        modifier = Modifier.padding(8.dp))
                }
                Spacer(modifier = Modifier.width(14.dp))
                Column {
                    Text(text = mainText,fontFamily = FontFamily.Default
                        ,color = Color.LightGray,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold)
                }

            }
            Icon(Icons.Default.KeyboardArrowRight, contentDescription = null,
                tint = Color.LightGray,
                modifier = Modifier.size(16.dp))
        }
    }
}
/**
 * Composable that renders an icon within a stylized box, used in various settings items.
 * @param icon The icon to be displayed inside the box.
 */
@Composable
fun HeaderText() {
    Text(text = "Settings", fontFamily = FontFamily.SansSerif, color = Purple80,
        textAlign = TextAlign.Center, modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp, bottom = 10.dp),
        fontWeight = FontWeight.ExtraBold, fontSize = 16.sp
    )
}
