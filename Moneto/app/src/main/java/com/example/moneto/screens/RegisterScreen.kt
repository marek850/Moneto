package com.example.moneto.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.moneto.R
import com.example.moneto.Screens
import com.example.moneto.ui.theme.Background
import com.example.moneto.ui.theme.Login

@Composable
fun RegisterScreen(navController: NavController) {
    val annotatedString = buildAnnotatedString {
        withStyle(style = SpanStyle(color = Color.LightGray)) {
            append("Already have an account? ")
        }
        withStyle(style = SpanStyle(color = Color.Cyan)) {
            append("Log in")
        }
    }
    Surface(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp)
            ) {
                Spacer(modifier = Modifier.height(40.dp))

                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Moneto Logo",
                    modifier = Modifier.size(180.dp)
                )

                Text(
                    text = "Sign up",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 24.dp)
                )

                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    label = { Text(buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color.White)) {
                            append("Email")
                        }
                        withStyle(style = SpanStyle(color = Color.Red)) {
                            append(" *")
                        }
                    }) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    label = { Text(buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color.White)) {
                            append("Password")
                        }
                        withStyle(style = SpanStyle(color = Color.Red)) {
                            append(" *")
                        }
                    }) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    label = { Text(buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color.White)) {
                            append("Confirm Password")
                        }
                        withStyle(style = SpanStyle(color = Color.Red)) {
                            append(" *")
                        }
                    }) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                Button(
                    onClick = {  },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    shape = MaterialTheme.shapes.medium,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Login
                    )
                ) {
                    Text("SIGN UP", modifier = Modifier.padding(8.dp), color = Color.Black)
                }


                ClickableText(
                    text = annotatedString,
                    style = TextStyle(
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    ),
                    onClick = { offset ->
                        if (offset in annotatedString.indexOf("Log in")..annotatedString.length) {
                            navController.navigate(Screens.Login.screen)
                        }
                    }
                )

                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}