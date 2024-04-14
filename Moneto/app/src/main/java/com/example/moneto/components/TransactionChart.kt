package com.example.moneto.components

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import co.yml.charts.common.model.PlotType
import co.yml.charts.ui.piechart.charts.DonutPieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData
import com.example.moneto.ui.theme.LightBackground
import com.example.moneto.ui.theme.Purple80

@Composable
@Preview
fun TransactionChart() {
    val donutChartData = PieChartData(
        slices = listOf(
            PieChartData.Slice("Expenses", 10f, Color.Red),
            PieChartData.Slice("Income", 20f, Color.Green)
        ), plotType = PlotType.Donut
    )
    val donutChartConfig = PieChartConfig(
        backgroundColor = LightBackground,
        labelColor = Purple80,
        labelVisible = true,
        strokeWidth = 20f,
        activeSliceAlpha = .9f,
        isAnimationEnable = true,
        labelType = PieChartConfig.LabelType.VALUE,
        isEllipsizeEnabled = true,
        showSliceLabels = true
    )
    DonutPieChart(
        modifier = Modifier
            .background(LightBackground),
        donutChartData,
        donutChartConfig
    )
}