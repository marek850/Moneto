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
import com.example.moneto.data.groupedByDayOfWeek
import com.example.moneto.ui.theme.Purple80
import com.github.tehras.charts.bar.BarChart
import com.github.tehras.charts.bar.BarChartData
import com.github.tehras.charts.bar.renderer.yaxis.SimpleYAxisDrawer
import java.time.DayOfWeek
import kotlin.math.abs
/**
 * Composable funkcia, ktorá zobrazuje týždenný graf transakcií v aplikácii Moneto.
 * Tento graf sumarizuje transakcie za každý deň v týždni a zobrazuje ich ako stĺpce v stĺpcovom grafe.
 * Farba každého stĺpca indikuje, či suma za deň bola príjem (zelená) alebo výdaj (červená).
 * Graf poskytuje rýchly prehľad o finančnej aktivite užívateľa počas celého týždňa.
 *
 * @param transactions Zoznam transakcií, ktoré majú byť zobrazené v grafe.
 */
@Composable
fun WeekChart(transactions: List<Transaction>) {
    val groupedTransactions = transactions.groupedByDayOfWeek()
    BarChart(barChartData = BarChartData(
        bars = listOf(
            BarChartData.Bar(
                label = DayOfWeek.MONDAY.name.substring(0, 1),
                value = abs(groupedTransactions[DayOfWeek.MONDAY.name]?.total?.toFloat() ?: 0f),
                color = if ((groupedTransactions[DayOfWeek.MONDAY.name]?.total?.toFloat()
                        ?: 0f) > 0
                ){ Color.Green } else{ Color.Red }
            ),
            BarChartData.Bar(
                label = DayOfWeek.TUESDAY.name.substring(0, 1),
                value = abs(groupedTransactions[DayOfWeek.TUESDAY.name]?.total?.toFloat() ?: 0f),
                color = if ((groupedTransactions[DayOfWeek.TUESDAY.name]?.total?.toFloat()
                        ?: 0f) > 0
                ){ Color.Green } else{ Color.Red }
            ),
            BarChartData.Bar(
                label = DayOfWeek.WEDNESDAY.name.substring(0, 1),
                value = abs(groupedTransactions[DayOfWeek.WEDNESDAY.name]?.total?.toFloat() ?: 0f),
                color = if ((groupedTransactions[DayOfWeek.WEDNESDAY.name]?.total?.toFloat()
                        ?: 0f) > 0
                ){ Color.Green } else{ Color.Red }
            ),
            BarChartData.Bar(
                label = DayOfWeek.THURSDAY.name.substring(0, 1),
                value = abs(groupedTransactions[DayOfWeek.THURSDAY.name]?.total?.toFloat() ?: 0f),
                color = if ((groupedTransactions[DayOfWeek.THURSDAY.name]?.total?.toFloat()
                        ?: 0f) > 0
                ){ Color.Green } else{ Color.Red }
            ),
            BarChartData.Bar(
                label = DayOfWeek.FRIDAY.name.substring(0, 1),
                value = abs(groupedTransactions[DayOfWeek.FRIDAY.name]?.total?.toFloat() ?: 0f),
                color = if ((groupedTransactions[DayOfWeek.FRIDAY.name]?.total?.toFloat()
                        ?: 0f) > 0
                ){ Color.Green } else{ Color.Red }
            ),
            BarChartData.Bar(
                label = DayOfWeek.SATURDAY.name.substring(0, 1),
                value = abs(groupedTransactions[DayOfWeek.SATURDAY.name]?.total?.toFloat() ?: 0f),
                color = if ((groupedTransactions[DayOfWeek.SATURDAY.name]?.total?.toFloat()
                        ?: 0f) > 0
                ){ Color.Green } else{ Color.Red }
            ),
            BarChartData.Bar(
                label = DayOfWeek.SUNDAY.name.substring(0, 1),
                value = abs(groupedTransactions[DayOfWeek.SUNDAY.name]?.total?.toFloat() ?: 0f),
                color = if ((groupedTransactions[DayOfWeek.SUNDAY.name]?.total?.toFloat()
                        ?: 0f) > 0
                ){ Color.Green } else{ Color.Red }
            ),
        )
    ),
       barDrawer = BarDrawer(TimeRange.Week),
        labelDrawer = LabelDrawer(TimeRange.Week),
        yAxisDrawer = SimpleYAxisDrawer(
            labelTextColor = Purple80,
            labelRatio = 7,
            labelTextSize = 14.sp
        ),
        modifier = Modifier.fillMaxSize().padding(bottom = 30.dp, end = 20.dp ))
}