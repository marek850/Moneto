package com.example.moneto.screens
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moneto.R
import com.example.moneto.ui.theme.Background
import com.example.moneto.ui.theme.Login


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun SettingsScreen() {
    Column(modifier = Modifier.background(Background).fillMaxHeight()) {
        HeaderText()
        ProfileCard()
        GeneralSettings()
        BudgetSettings()
    }
}

@Composable
fun BudgetSettings() {
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
        GeneralSettingsItem(icon = Icons.Default.AccountBox, mainText = "Currency", onClick = {} )
        GeneralSettingsItem(icon = Icons.Default.Build, mainText = "Limit", onClick = {} )
    }
}

@Composable
fun GeneralSettings() {
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
        GeneralSettingsItem(icon = Icons.Default.Person, mainText = "Basic Settings", onClick = {} )
        GeneralSettingsItem(icon = Icons.Default.Menu, mainText = "Categories", onClick = {} )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GeneralSettingsItem(
    icon: ImageVector,
    mainText:String,
    onClick:()->Unit
) {
    Card(onClick = onClick,
            colors = CardDefaults.cardColors(
            containerColor = Color(0xFF483A6B)
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
                        Color(0xFF483A6B)
                    ))
                {
                    Icon(icon, contentDescription = null,
                        tint = Color.LightGray,
                        modifier = Modifier.padding(8.dp))
                }
                Spacer(modifier = Modifier.width(14.dp))
                Column() {
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

@Composable
fun ProfileCard() {
    Card(modifier = Modifier
        .fillMaxWidth()
        .height(100.dp)
        .padding(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF483A6B)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        )) {
        Row(modifier = Modifier.padding(20.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Column(modifier = Modifier.weight(2f)) {
                Text(text = "My Account",
                    fontFamily = FontFamily.Monospace
                    ,color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(text = "marekmagula906@gmail.com",
                    fontFamily = FontFamily.Serif
                    ,color = Color.LightGray,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Image(painter = painterResource(id = R.drawable.profile_icon), contentDescription = null
            , modifier = Modifier
                    .weight(1f) // Assign weight for the image to take up one third of the available space
                    .aspectRatio(0.2f) // Maintain a 1:1 aspect ratio (you can adjust this as needed)
                    .fillMaxHeight(),
                contentScale = ContentScale.Fit)
        }
    }
}

@Composable
fun HeaderText() {
    Text(text = "Settings", fontFamily = FontFamily.SansSerif, color = Login,
        textAlign = TextAlign.Center, modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp, bottom = 10.dp),
        fontWeight = FontWeight.ExtraBold, fontSize = 16.sp
    )
}
