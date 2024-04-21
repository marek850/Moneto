package com.example.moneto.charts

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moneto.data.TimeRange
import com.example.moneto.ui.theme.Purple40
import com.example.moneto.ui.theme.Purple80
import com.github.tehras.charts.bar.BarChart
import com.github.tehras.charts.bar.BarChartData
import com.github.tehras.charts.bar.renderer.yaxis.SimpleYAxisDrawer

@Composable
fun DayChart() {
    val numberOfHours = 24

    BarChart(
        barChartData = BarChartData(
            bars = buildList() {
                for (i in 1..numberOfHours) {
                    add(
                        BarChartData.Bar(
                            label = "$i",
                            value = 5f,
                            color = Purple40,
                        ))
                }
            }
        ),
        labelDrawer = LabelDrawer(TimeRange.Day),
        yAxisDrawer = SimpleYAxisDrawer(
            labelTextColor = Purple80,
            labelRatio = 7,
            labelTextSize = 14.sp
        ),
        barDrawer = BarDrawer(TimeRange.Day),
        modifier = Modifier.fillMaxSize().padding(bottom = 30.dp, end = 20.dp )
    )
}