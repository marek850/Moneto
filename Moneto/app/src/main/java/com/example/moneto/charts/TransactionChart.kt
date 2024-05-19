package com.example.moneto.charts

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import co.yml.charts.common.model.PlotType
import co.yml.charts.ui.piechart.charts.DonutPieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData
import com.example.moneto.ui.theme.LightBackground
import com.example.moneto.ui.theme.Purple80
import kotlin.math.abs
/**
 * Composable funkcia, ktorá zobrazuje graf sumarizujúci príjmy a výdavky v aplikácii Moneto.
 * Tento graf je vizualizovaný ako donutový koláčový graf, kde každý výsek reprezentuje buď príjmy alebo výdavky
 * s príslušnou farbou a veľkosťou podľa ich hodnoty. Graf umožňuje rýchly vizuálny prehľad o finančnej situácii užívateľa.
 *
 * @param expenses Celková suma výdavkov, ktorá bude zobrazená v grafe.
 * @param income Celková suma príjmov, ktorá bude zobrazená v grafe.
 */
@Composable
fun TransactionChart(expenses: Double, income: Double) {
    val donutChartData = PieChartData(
        slices = listOf(
            PieChartData.Slice("Expenses", abs(expenses).toFloat(), Color.Red),
            PieChartData.Slice("Income", income.toFloat(), Color.Green)
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