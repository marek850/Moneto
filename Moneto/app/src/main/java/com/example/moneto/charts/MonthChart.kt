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
import com.example.moneto.data.groupedByDayOfMonth
import com.example.moneto.ui.theme.Purple80
import com.github.tehras.charts.bar.BarChart
import com.github.tehras.charts.bar.BarChartData
import com.github.tehras.charts.bar.renderer.yaxis.SimpleYAxisDrawer
import java.time.LocalDate
import java.time.YearMonth
import kotlin.math.abs
/**
 * Composable funkcia, ktorá zobrazuje mesačný graf transakcií v aplikácii Moneto.
 * Tento graf sumarizuje transakcie za každý deň v mesiaci a zobrazuje ich ako stĺpce v stĺpcovom grafe.
 * Farba každého stĺpca indikuje, či suma za deň bola príjem (zelená) alebo výdaj (červená).
 * Graf poskytuje rýchly prehľad o finančnej aktivite užívateľa počas vybraného mesiaca.
 *
 * @param transactions Zoznam transakcií, ktoré majú byť zobrazené v grafe.
 * @param month Dátum, ktorý určuje mesiac pre graf. Používa sa na výpočet počtu dní v mesiaci.
 */
@Composable
fun MonthChart(transactions: List<Transaction>,month: LocalDate) {
    val numberOfDays = YearMonth.of(month.year, month.month).lengthOfMonth()
    val groupedTransactions = transactions.groupedByDayOfMonth()

    BarChart(
        barChartData = BarChartData(
            bars = buildList() {
                for (i in 1..numberOfDays) {
                    add(
                        BarChartData.Bar(
                        label = "$i",
                        value = abs(groupedTransactions[i]?.total?.toFloat()
                            ?: 0f),
                        color = if ((groupedTransactions[i]?.total?.toFloat() ?: 0f) > 0){ Color.Green } else{ Color.Red },
                    ))
                }
            }
        ),
        labelDrawer = LabelDrawer(TimeRange.Month),
        yAxisDrawer = SimpleYAxisDrawer(
            labelTextColor = Purple80,
            labelRatio = 7,
            labelTextSize = 14.sp
        ),
        barDrawer = BarDrawer(TimeRange.Month),
        modifier = Modifier.fillMaxSize().padding(bottom = 30.dp, end = 20.dp )
    )
}