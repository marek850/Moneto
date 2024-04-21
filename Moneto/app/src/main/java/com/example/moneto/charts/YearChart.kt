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
import java.time.Month

@Composable
fun YearChart() {
    BarChart(
        barChartData = BarChartData(
            bars = listOf(
                BarChartData.Bar(
                    label = Month.JANUARY.name.substring(0, 1),
                    value = 10f//groupedExpenses[Month.JANUARY.name]?.total?.toFloat()
                        ?: 0f,
                    color = Purple40,
                ),
                BarChartData.Bar(
                    label = Month.FEBRUARY.name.substring(0, 1),
                    value = 8f,//groupedExpenses[Month.FEBRUARY.name]?.total?.toFloat() ?: 0f,
                    color = Purple40
                ),
                BarChartData.Bar(
                    label = Month.MARCH.name.substring(0, 1),
                    value = 3f,//groupedExpenses[Month.MARCH.name]?.total?.toFloat() ?: 0f,
                    color = Purple40
                ),
                BarChartData.Bar(
                    label = Month.APRIL.name.substring(0, 1),
                    value = 2f,//groupedExpenses[Month.APRIL.name]?.total?.toFloat() ?: 0f,
                    color = Purple40
                ),
                BarChartData.Bar(
                    label = Month.MAY.name.substring(0, 1),
                    value = 12f,//groupedExpenses[Month.MAY.name]?.total?.toFloat() ?: 0f,
                    color = Purple40
                ),
                BarChartData.Bar(
                    label = Month.JUNE.name.substring(0, 1),
                    value = 15f,//groupedExpenses[Month.JUNE.name]?.total?.toFloat() ?: 0f,
                    color = Purple40
                ),
                BarChartData.Bar(
                    label = Month.JULY.name.substring(0, 1),
                    value = 0.2f,//groupedExpenses[Month.JULY.name]?.total?.toFloat() ?: 0f,
                    color = Purple40
                ),
                BarChartData.Bar(
                    label = Month.AUGUST.name.substring(0, 1),
                    value = 0f,//groupedExpenses[Month.AUGUST.name]?.total?.toFloat() ?: 0f,
                    color = Purple40
                ),
                BarChartData.Bar(
                    label = Month.SEPTEMBER.name.substring(0, 1),
                    value = 5f,//groupedExpenses[Month.SEPTEMBER.name]?.total?.toFloat() ?: 0f,
                    color = Purple40
                ),
                BarChartData.Bar(
                    label = Month.OCTOBER.name.substring(0, 1),
                    value = 2f,//groupedExpenses[Month.OCTOBER.name]?.total?.toFloat() ?: 0f,
                    color = Purple40
                ),
                BarChartData.Bar(
                    label = Month.NOVEMBER.name.substring(0, 1),
                    value = 4f,//groupedExpenses[Month.NOVEMBER.name]?.total?.toFloat() ?: 0f,
                    color = Purple40
                ),
                BarChartData.Bar(
                    label = Month.DECEMBER.name.substring(0, 1),
                    value = 20f,//groupedExpenses[Month.DECEMBER.name]?.total?.toFloat() ?: 0f,
                    color = Purple40
                ),
            )
        ),
        labelDrawer = LabelDrawer(TimeRange.Year),
        yAxisDrawer = SimpleYAxisDrawer(
            labelTextColor = Purple80,
            labelRatio = 7,
            labelTextSize = 14.sp
        ),
        barDrawer = BarDrawer(TimeRange.Week),
        modifier = Modifier.fillMaxSize().padding(bottom = 30.dp, end = 20.dp )
    )
}