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
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.Send
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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun Categories(navController: NavController, categoriesModel: CategoriesViewModel = viewModel()) {
    val uiState by categoriesModel.uiState.collectAsState()

    val colorPickerController = rememberColorPickerController()

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
                            Icons.Rounded.KeyboardArrowLeft, tint = Purple80,contentDescription = "Settings"
                        )
                        Text("Settings", color = Purple80, )
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
                            key = { _, category -> category.name }) { index, category ->

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
                                            Icons.Rounded.ArrowBack,
                                            tint = Purple80,
                                            contentDescription = "Delete transaction",
                                            modifier = Modifier.size(15.dp)
                                        )
                                    }
                                }
                            }
                            /*if (index < uiState.categories.size - 1) {
                                Row(modifier = Modifier.background(LightBackground).height(1.dp)) {
                                    Divider(
                                        modifier = Modifier.padding(start = 16.dp,end = 16.dp),
                                        thickness = 1.dp,
                                        color = Color.LightGray
                                    )
                                }
                            }*/
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
                /*if (uiState.colorPickerShowing) {
                    Dialog(onDismissRequest = categoriesModel::hideColorPicker) {
                        Surface(color = Background) {
                            Column(
                                modifier = Modifier.padding(all = 30.dp)
                            ) {
                                Text("Select a color", style = Typography.titleLarge)
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 24.dp),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    AlphaTile(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(60.dp)
                                            .clip(RoundedCornerShape(6.dp)),
                                        controller = colorPickerController
                                    )
                                }
                                HsvColorPicker(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(300.dp)
                                        .padding(10.dp),
                                    controller = colorPickerController,
                                    onColorChanged = { envelope ->
                                        categoriesModel.setNewCategoryColor(envelope.color)
                                    },
                                )
                                TextButton(
                                    onClick = categoriesModel::hideColorPicker,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 24.dp),
                                ) {
                                    Text("Done")
                                }
                            }
                        }
                    }
                }*/
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
                            modifier = Modifier
                                .fillMaxWidth()
                                ,
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
                        Icons.Rounded.Send,
                        tint = Purple80,
                        contentDescription = "Create category"
                    )
                }
            }
        }
    })
}

@Preview()
@Composable
fun CategoriesPreview() {

    Categories(navController = rememberNavController())

}
