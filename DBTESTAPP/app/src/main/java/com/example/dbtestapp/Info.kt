package com.example.dbtestapp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable

import androidx.compose.ui.graphics.Color

@Composable
fun InfoScreen() {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFF201a30)) // Use the provided dark background color
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "MONETO",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 24.dp),
                textAlign = TextAlign.Center
            )
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Company Logo",
                modifier = Modifier.size(150.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Moneto is a pioneering finance company dedicated to enhancing personal financial management through its user-friendly budgeting app. Our mission is to empower individuals to achieve financial freedom by providing intuitive tools for budgeting, expense tracking, and insightful financial planning, making personal finance accessible and manageable for everyone.",
                color = Color.Gray,
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 24.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}