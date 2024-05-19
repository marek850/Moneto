package com.example.moneto.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.rounded.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.moneto.R
import com.example.moneto.components.CustomRow
import com.example.moneto.components.CustomTextField
import com.example.moneto.ui.theme.Background
import com.example.moneto.ui.theme.Destructive
import com.example.moneto.ui.theme.LightBackground
import com.example.moneto.ui.theme.Purple80
import com.example.moneto.ui.theme.Typography
import com.example.moneto.view_models.CategoriesViewModel
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox
/**
 * Composable funkcia, ktorá zobrazuje obrazovku správy kategórií v aplikácii Moneto.
 * Táto obrazovka umožňuje používateľom zobraziť, vytvoriť a odstrániť kategórie, čím zlepšuje ich organizačné schopnosti
 * pri spracovaní transakcií. Rozhranie obsahuje dynamický zoznam kategórií s funkciou mazania potiahnutím.
 *
 * @param navController NavController, ktorý zabezpečuje navigáciu v aplikácii, uľahčuje navigáciu na predchádzajúce obrazovky.
 * @param categoriesModel ViewModel, ktorý riadi a poskytuje stav pre operácie súvisiace s kategóriami.
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun Categories(navController: NavController, categoriesModel: CategoriesViewModel = viewModel()) {
    val uiState by categoriesModel.uiState.collectAsState()

    rememberColorPickerController()

    Scaffold(modifier = Modifier
        .background(Background)
        .fillMaxHeight(),topBar = {
        MediumTopAppBar(title = { Text("Categories", color = Purple80) },
            colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = Background
            ),
            navigationIcon = {
                Surface(
                    onClick = navController::popBackStack,
                    color = Color.Transparent,
                ) {
                    Row(modifier = Modifier.padding(vertical = 10.dp)) {
                        Icon(
                            Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                            tint = Purple80,
                            contentDescription = "Settings"
                        )
                        Text("Settings", color = Purple80)
                    }
                }
            })
    }, content = { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxHeight()
                .background(Background),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(modifier = Modifier
                .padding(16.dp)) {
                AnimatedVisibility(visible = true) {
                    LazyColumn(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(30.dp))
                    ) {
                        itemsIndexed(
                            uiState.categories,
                            key = { _, category -> category.name }) { _, category ->

                            SwipeableActionsBox(
                                endActions = listOf(
                                    SwipeAction(
                                        icon = painterResource(R.drawable.delete),
                                        background = Destructive,
                                        onSwipe = { categoriesModel.deleteCategory(category) }
                                    ),
                                ),
                                modifier = Modifier
                                    .animateItemPlacement()
                                    .clip(shape = RoundedCornerShape(30.dp))
                                    .padding(vertical = 10.dp)
                                    .background(
                                        LightBackground
                                    )
                            ) {
                                CustomRow(modifier = Modifier
                                    .background(LightBackground)) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(horizontal = 16.dp)
                                    ) {
                                        Text(
                                            category.name,
                                            modifier = Modifier.padding(
                                                horizontal = 16.dp,
                                                vertical = 10.dp
                                            ),
                                            style = Typography.titleMedium,
                                        )
                                        Spacer(modifier = Modifier.weight(1f))
                                        Icon(
                                            Icons.AutoMirrored.Rounded.ArrowBack,
                                            tint = Purple80,
                                            contentDescription = "Delete transaction",
                                            modifier = Modifier.size(15.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Surface(
                    color = LightBackground,
                    modifier = Modifier
                        .height(44.dp)
                        .weight(1f)
                        .padding(start = 16.dp).clip(RoundedCornerShape(20.dp)),
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        CustomTextField(
                            value = uiState.categoryName,
                            onValueChange = categoriesModel::setNewCategoryName,
                            placeholder = { Text("Category name", color = Color.White
                            ) },
                            maxLines = 1,
                        )
                    }
                }
                IconButton(
                    onClick = categoriesModel::createNewCategory,
                    modifier = Modifier
                        .padding(start = 16.dp)
                ) {
                    Icon(
                        Icons.AutoMirrored.Rounded.Send,
                        tint = Purple80,
                        contentDescription = "Create category"
                    )
                }
            }
        }
    })
}

@Preview
@Composable
fun CategoriesPreview() {

    Categories(navController = rememberNavController())

}
