package com.example.moneto.charts

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moneto.data.TimeRange
import com.example.moneto.data.Transaction
import com.example.moneto.data.groupedByMonth
import com.example.moneto.ui.theme.Purple80
import com.github.tehras.charts.bar.BarChart
import com.github.tehras.charts.bar.BarChartData
import com.github.tehras.charts.bar.renderer.yaxis.SimpleYAxisDrawer
import java.time.Month
import kotlin.math.abs
/**
 * Composable funkcia, ktorá zobrazuje ročný graf transakcií v aplikácii Moneto.
 * Tento graf sumarizuje transakcie za každý mesiac roka a zobrazuje ich ako stĺpce v stĺpcovom grafe.
 * Farba každého stĺpca indikuje, či suma za mesiac bola príjem (zelená) alebo výdaj (červená).
 * Graf poskytuje rýchly prehľad o finančnej aktivite užívateľa počas celého roka.
 *
 * @param transactions Zoznam transakcií, ktoré majú byť zobrazené v grafe.
 */
@Composable
fun YearChart(transactions: List<Transaction>) {
    val groupedTransactions = transactions.groupedByMonth()
    BarChart(
        barChartData = BarChartData(
            bars = listOf(
                BarChartData.Bar(
                    label = Month.JANUARY.name.substring(0, 1),
                    value = abs(groupedTransactions[Month.JANUARY.name]?.total?.toFloat()?: 0f),
                    color = if ((groupedTransactions[Month.JANUARY.name]?.total?.toFloat()
                            ?: 0f) > 0
                    ){ Color.Green } else{ Color.Red }
                ),
                BarChartData.Bar(
                    label = Month.FEBRUARY.name.substring(0, 1),
                    value = abs(groupedTransactions[Month.FEBRUARY.name]?.total?.toFloat() ?: 0f),
                    color = if ((groupedTransactions[Month.FEBRUARY.name]?.total?.toFloat()
                            ?: 0f) > 0
                    ){ Color.Green } else{ Color.Red }
                ),
                BarChartData.Bar(
                    label = Month.MARCH.name.substring(0, 1),
                    value = abs(groupedTransactions[Month.MARCH.name]?.total?.toFloat() ?: 0f),
                    color = if ((groupedTransactions[Month.MARCH.name]?.total?.toFloat() ?: 0f) > 0){ Color.Green } else{ Color.Red }
                ),
                BarChartData.Bar(
                    label = Month.APRIL.name.substring(0, 1),
                    value = abs(groupedTransactions[Month.APRIL.name]?.total?.toFloat() ?: 0f),
                    color = if ((groupedTransactions[Month.APRIL.name]?.total?.toFloat() ?: 0f) > 0){ Color.Green } else{ Color.Red }
                ),
                BarChartData.Bar(
                    label = Month.MAY.name.substring(0, 1),
                    value = abs(groupedTransactions[Month.MAY.name]?.total?.toFloat() ?: 0f),
                    color = if ((groupedTransactions[Month.MAY.name]?.total?.toFloat() ?: 0f) > 0){ Color.Green } else{ Color.Red }
                ),
                BarChartData.Bar(
                    label = Month.JUNE.name.substring(0, 1),
                    value = abs(groupedTransactions[Month.JUNE.name]?.total?.toFloat() ?: 0f),
                    color = if ((groupedTransactions[Month.JUNE.name]?.total?.toFloat() ?: 0f) > 0){ Color.Green } else{ Color.Red }
                ),
                BarChartData.Bar(
                    label = Month.JULY.name.substring(0, 1),
                    value = abs(groupedTransactions[Month.JULY.name]?.total?.toFloat() ?: 0f),
                    color = if ((groupedTransactions[Month.JULY.name]?.total?.toFloat() ?: 0f) > 0){ Color.Green } else{ Color.Red }
                ),
                BarChartData.Bar(
                    label = Month.AUGUST.name.substring(0, 1),
                    value = abs(groupedTransactions[Month.AUGUST.name]?.total?.toFloat() ?: 0f),
                    color = if ((groupedTransactions[Month.AUGUST.name]?.total?.toFloat()
                            ?: 0f) > 0
                    ){ Color.Green } else{ Color.Red }
                ),
                BarChartData.Bar(
                    label = Month.SEPTEMBER.name.substring(0, 1),
                    value = abs(groupedTransactions[Month.SEPTEMBER.name]?.total?.toFloat() ?: 0f),
                    color = if ((groupedTransactions[Month.SEPTEMBER.name]?.total?.toFloat()
                            ?: 0f) > 0
                    ){ Color.Green } else{ Color.Red }
                ),
                BarChartData.Bar(
                    label = Month.OCTOBER.name.substring(0, 1),
                    value = abs(groupedTransactions[Month.OCTOBER.name]?.total?.toFloat() ?: 0f),
                    color = if ((groupedTransactions[Month.OCTOBER.name]?.total?.toFloat()
                            ?: 0f) > 0
                    ){ Color.Green } else{ Color.Red }
                ),
                BarChartData.Bar(
                    label = Month.NOVEMBER.name.substring(0, 1),
                    value = abs(groupedTransactions[Month.NOVEMBER.name]?.total?.toFloat() ?: 0f),
                    color = if ((groupedTransactions[Month.NOVEMBER.name]?.total?.toFloat()
                            ?: 0f) > 0
                    ){ Color.Green } else{ Color.Red }
                ),
                BarChartData.Bar(
                    label = Month.DECEMBER.name.substring(0, 1),
                    value = abs(groupedTransactions[Month.DECEMBER.name]?.total?.toFloat() ?: 0f),
                    color = if ((groupedTransactions[Month.DECEMBER.name]?.total?.toFloat()
                            ?: 0f) > 0
                    ){ Color.Green } else{ Color.Red }
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