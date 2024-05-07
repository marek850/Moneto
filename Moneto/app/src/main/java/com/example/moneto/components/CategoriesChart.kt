package com.example.moneto.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.moneto.charts.SliceDrawer
import com.github.tehras.charts.piechart.PieChart
import com.github.tehras.charts.piechart.PieChartData
/**
 * Composable funkcia, ktorá zobrazuje koláčový graf kategórií v aplikácii Moneto.
 * Tento graf vizualizuje rozdelenie určitých dát v podobe koláčového grafu, kde každý výsek predstavuje rôzne kategórie alebo hodnoty.
 * Každý výsek má priradenú farbu a veľkosť, ktorá reprezentuje jeho proporcionálny podiel na celku.
 *
 */
@Composable
@Preview
fun CategoriesChart() {
    PieChart(pieChartData = PieChartData(slices = listOf(
        PieChartData.Slice(2f, Color.Cyan),
        PieChartData.Slice(2f, Color.Blue),
        PieChartData.Slice(1f, Color.Red),
        PieChartData.Slice(4f, Color.Magenta)
    )),
        modifier = Modifier.fillMaxSize().padding(bottom = 30.dp),
        sliceDrawer = SliceDrawer()

    )
}