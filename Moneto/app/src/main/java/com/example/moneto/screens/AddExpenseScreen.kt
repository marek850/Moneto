@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.moneto.screens

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.moneto.components.CustomRow
import com.example.moneto.components.CustomTextField
import com.example.moneto.ui.theme.Background
import com.example.moneto.ui.theme.LightBackground
import com.example.moneto.ui.theme.LightPurple
import com.example.moneto.ui.theme.Purple80
import java.util.Calendar

@Preview
@Composable
fun AddExpense() {
    val categories = listOf("Groceries", "Bills", "Restaurants")
    var chosenCategory by remember {
        mutableStateOf(categories[0])
    }
    val mContext = LocalContext.current
    val mYear: Int
    val mMonth: Int
    val mDay: Int

    val mCalendar = Calendar.getInstance()
    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH) + 1
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)
    var mDate by remember {
        mutableStateOf("${mCalendar.get(Calendar.DAY_OF_MONTH)}/${mCalendar.get(Calendar.MONTH)}/${mCalendar.get(Calendar.YEAR)}")
    }
    val mDatePicker = DatePickerDialog(
        mContext,{
            _: DatePicker, chosenYear: Int, chosenMonth: Int, chosenDay: Int ->
            mDate = "${chosenDay}/${chosenMonth + 1}/${chosenYear}"
        },
        mYear,
        mMonth,
        mDay
        )
    mDatePicker.datePicker.maxDate = mCalendar.timeInMillis


    Scaffold(topBar = {
        MediumTopAppBar(
            title = { Text("Add", color = Color.White) },
            colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = Background
            )
        )
    }, content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .background(Background)
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Box (modifier = Modifier
                    .padding(16.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(LightBackground)
                ){
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()

                    ) {
                        CustomRow(label = "Description",){
                            CustomTextField(value = "New Iphone etc.",modifier = Modifier.fillMaxWidth() ,keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                            ), onValueChange = {}
                            )
                        }
                        Divider(
                            modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                            thickness = 1.dp,
                            color = Color.LightGray
                        )
                        CustomRow(
                            label = "Amount"
                        ){
                            CustomTextField(value = "Amount",modifier = Modifier.fillMaxWidth() ,keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                            ), onValueChange = {}
                            )
                        }
                        Divider(
                            modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                            thickness = 1.dp,
                            color = Color.LightGray
                        )
                        CustomRow(
                            label = "Date"
                        ){
                            TextButton(onClick = { mDatePicker.show() }) {
                                Text(mDate, color = Purple80)
                            }
                        }
                        Divider(
                            modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                            thickness = 1.dp,
                            color = Color.LightGray
                        )

                        CustomRow(label = "Category") {
                            var categoriesMenuOpen by remember {
                                mutableStateOf(false)
                            }
                            TextButton(onClick = {categoriesMenuOpen = true}) {
                                Text(chosenCategory, color = Purple80)
                                DropdownMenu(expanded = categoriesMenuOpen, onDismissRequest = { categoriesMenuOpen = false }) {
                                    categories.forEach { category ->
                                    DropdownMenuItem(text = { Text(category, color = Purple80) },
                                        onClick = { chosenCategory = category
                                        categoriesMenuOpen = false})
                                    }
                                }
                            }
                        }
                    }
                }
                Button(
                    onClick = {},
                    modifier = Modifier.padding(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LightPurple
                    )
                ) {
                    Text("Add expense", color = Color.White)
                }
            }




    })
}