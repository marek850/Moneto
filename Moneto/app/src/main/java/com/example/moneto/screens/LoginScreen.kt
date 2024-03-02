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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moneto.R
import com.example.moneto.ui.theme.Background
import com.example.moneto.ui.theme.Login

@Preview(showBackground = true)
@Composable
fun LoginScreen() {
    val annotatedString = buildAnnotatedString {
        withStyle(style = SpanStyle(color = Color.LightGray)) {
            append("Don't have an account? ")
        }
        withStyle(style = SpanStyle(color = Color.Cyan)) {
            append("Sign up")
        }
    }
    Surface(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Background) // Use the provided dark background color
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp)
            ) {
                Spacer(modifier = Modifier.height(40.dp)) // Adjust as per your UI

                // Top section - Illustration or logo
                Image(
                    painter = painterResource(id = R.drawable.logo), // Replace with your actual resource
                    contentDescription = "Login Illustration",
                    modifier = Modifier.size(180.dp) // Adjust size as needed
                )

                // Login title and subtitle
                Text(
                    text = "Login",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 24.dp)
                )
                Text(
                    text = "Please sign in to continue.",
                    color = Color.Gray,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                // Email text field
                OutlinedTextField(
                    value = "", // Replace with state variable
                    onValueChange = {}, // Handle updates
                    label = { Text("Email", color = Color.White) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                // Password text field
                OutlinedTextField(
                    value = "", // Replace with state variable
                    onValueChange = {}, // Handle updates
                    label = { Text("Password", color = Color.White) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                // Login button
                Button(
                    onClick = { /* Handle login */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    shape = MaterialTheme.shapes.medium,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Login
                    )
                ) {
                    Text("LOGIN", modifier = Modifier.padding(8.dp), color = Color.Black)
                }


                ClickableText(
                    text = annotatedString,
                    style = TextStyle(
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    ),
                    onClick = { offset ->
                        // Handle click event, check if 'Sign up' part is clicked
                        if (offset in annotatedString.indexOf("Sign up")..annotatedString.length) {
                            // Handle sign up click
                        }
                    }
                )

                Spacer(modifier = Modifier.weight(1f)) // Pushes everything to the top
            }
        }
    }
    /*Surface {
        Column (modifier = Modifier.fillMaxSize()){
            val uiColor = if (isSystemInDarkTheme()) Color.White else Color.Black

            Box (contentAlignment = Alignment.TopCenter) {
                Image(
                    painter = painterResource(id = R.drawable.shape),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(fraction = 0.46f),
                    contentScale = ContentScale.FillBounds
                )
                Row (verticalAlignment = Alignment.CenterVertically)
                {
                    Row{Image(modifier = Modifier.size(200.dp),painter = painterResource(id = R.drawable.logo), contentDescription = stringResource(
                        id = R.string.app_logo
                    ))}
                    Row {
                        Text(text = stringResource(id = R.string.app_name), style = MaterialTheme.typography.headlineMedium,textAlign = TextAlign.Center,
                            color = uiColor)
                    }
                    Spacer(modifier = Modifier.width(15.dp))

                }


            }
        }
    }*/
}