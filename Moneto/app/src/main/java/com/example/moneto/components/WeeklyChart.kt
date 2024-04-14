package com.example.moneto.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moneto.BarDrawer
import com.example.moneto.LabelDrawer
import com.example.moneto.ui.theme.Purple40
import com.example.moneto.ui.theme.Purple80
import com.github.tehras.charts.bar.BarChart
import com.github.tehras.charts.bar.BarChartData
import com.github.tehras.charts.bar.renderer.yaxis.SimpleYAxisDrawer
import java.time.DayOfWeek

@Preview
@Composable
fun WeekChart() {
    BarChart(barChartData = BarChartData(
        bars = listOf(
            BarChartData.Bar(
                label = DayOfWeek.MONDAY.name.substring(0, 1),
                value = 10.3F,
                color = Purple40,
            ),
            BarChartData.Bar(
                label = DayOfWeek.TUESDAY.name.substring(0, 1),
                value = 05.3F,
                color = Purple40
            ),
            BarChartData.Bar(
                label = DayOfWeek.WEDNESDAY.name.substring(0, 1),
                value = 6.3F,
                color = Purple40
            ),
            BarChartData.Bar(
                label = DayOfWeek.THURSDAY.name.substring(0, 1),
                value = 2.3F,
                color = Purple40
            ),
            BarChartData.Bar(
                label = DayOfWeek.FRIDAY.name.substring(0, 1),
                value = 6.8F,
                color = Purple40
            ),
            BarChartData.Bar(
                label = DayOfWeek.SATURDAY.name.substring(0, 1),
                value = 15.0F,
                color = Purple40
            ),
            BarChartData.Bar(
                label = DayOfWeek.SUNDAY.name.substring(0, 1),
                value = 12.2F,
                color = Purple40
            ),
        )
    ),
       barDrawer = BarDrawer(),
        labelDrawer = LabelDrawer(),
        yAxisDrawer = SimpleYAxisDrawer(
            labelTextColor = Purple80,
            labelRatio = 7,
            labelTextSize = 14.sp
        ),
        modifier = Modifier.fillMaxSize().padding(bottom = 30.dp))

}