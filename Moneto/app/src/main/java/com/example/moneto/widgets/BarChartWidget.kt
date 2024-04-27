package com.example.moneto.widgets

import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import com.example.moneto.charts.BarDrawer
import com.example.moneto.charts.LabelDrawer
import com.example.moneto.data.TimeRange
import com.example.moneto.ui.theme.Purple80
import com.github.tehras.charts.bar.BarChart
import com.github.tehras.charts.bar.BarChartData
import com.github.tehras.charts.bar.renderer.yaxis.SimpleYAxisDrawer
import java.time.DayOfWeek

object BarChartWidget: GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {

        provideContent {
            BarChart(
                barChartData = BarChartData(
                    bars = listOf(
                        BarChartData.Bar(
                            label = DayOfWeek.MONDAY.name.substring(0, 1),
                            value = 2f,//abs(groupedTransactions[DayOfWeek.MONDAY.name]?.total?.toFloat() ?: 0f),
                            color = Color.Green//if (groupedTransactions[DayOfWeek.MONDAY.name]?.total?.toFloat() ?: 0f > 0){ Color.Green } else{ Color.Red }
                        ),
                        BarChartData.Bar(
                            label = DayOfWeek.TUESDAY.name.substring(0, 1),
                            value = 2f,//abs(groupedTransactions[DayOfWeek.TUESDAY.name]?.total?.toFloat() ?: 0f),
                            color = Color.Green //if (groupedTransactions[DayOfWeek.TUESDAY.name]?.total?.toFloat() ?: 0f > 0){ Color.Green } else{ Color.Red }
                        ),
                        BarChartData.Bar(
                            label = DayOfWeek.WEDNESDAY.name.substring(0, 1),
                            value = 2f,//abs(groupedTransactions[DayOfWeek.WEDNESDAY.name]?.total?.toFloat() ?: 0f),
                            color = Color.Green//if (groupedTransactions[DayOfWeek.WEDNESDAY.name]?.total?.toFloat() ?: 0f > 0){ Color.Green } else{ Color.Red }
                        ),
                        BarChartData.Bar(
                            label = DayOfWeek.THURSDAY.name.substring(0, 1),
                            value = 2f,//abs(groupedTransactions[DayOfWeek.THURSDAY.name]?.total?.toFloat() ?: 0f),
                            color = Color.Green//if (groupedTransactions[DayOfWeek.THURSDAY.name]?.total?.toFloat() ?: 0f > 0){ Color.Green } else{ Color.Red }
                        ),
                        BarChartData.Bar(
                            label = DayOfWeek.FRIDAY.name.substring(0, 1),
                            value = 2f,//abs(groupedTransactions[DayOfWeek.FRIDAY.name]?.total?.toFloat() ?: 0f),
                            color = Color.Green//if (groupedTransactions[DayOfWeek.FRIDAY.name]?.total?.toFloat() ?: 0f > 0){ Color.Green } else{ Color.Red }
                        ),
                        BarChartData.Bar(
                            label = DayOfWeek.SATURDAY.name.substring(0, 1),
                            value = 2f,//abs(groupedTransactions[DayOfWeek.SATURDAY.name]?.total?.toFloat() ?: 0f),
                            color = Color.Green//if (groupedTransactions[DayOfWeek.SATURDAY.name]?.total?.toFloat() ?: 0f > 0){ Color.Green } else{ Color.Red }
                        ),
                        BarChartData.Bar(
                            label = DayOfWeek.SUNDAY.name.substring(0, 1),
                            value = 2f,//abs(groupedTransactions[DayOfWeek.SUNDAY.name]?.total?.toFloat() ?: 0f),
                            color = Color.Green//if (groupedTransactions[DayOfWeek.SUNDAY.name]?.total?.toFloat() ?: 0f > 0){ Color.Green } else{ Color.Red }
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
                modifier = Modifier.fillMaxSize().padding(bottom = 30.dp, end = 20.dp)
            )
        }
    }
}