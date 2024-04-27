package com.example.moneto.charts

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas
import com.example.moneto.data.TimeRange
import com.example.moneto.ui.theme.Purple80
import com.github.tehras.charts.piechart.utils.toLegacyInt

class LabelDrawer(private val timeRange: TimeRange, private val lastDay: Int? = -1) :
    com.github.tehras.charts.bar.renderer.label.LabelDrawer {
    private val leftOffset = when (timeRange) {
        TimeRange.Day -> 20f
        TimeRange.Week -> 50f
        TimeRange.Month -> 13f
        TimeRange.Year -> 32f
    }
    private val paint = android.graphics.Paint().apply {
        this.textAlign = android.graphics.Paint.Align.CENTER
        this.color = Purple80.toLegacyInt()
        this.textSize = 42f
    }

    override fun drawLabel(
        drawScope: DrawScope,
        canvas: Canvas,
        label: String,
        barArea: Rect,
        xAxisArea: Rect
    ) {
        val monthlyCondition =
            timeRange == TimeRange.Month && (
                    Integer.parseInt(label) % 5 == 0 ||
                            Integer.parseInt(label) == 1 ||
                            Integer.parseInt(label) == lastDay
                    )
        val dailyCondition = timeRange == TimeRange.Day && (Integer.parseInt(label) % 6 == 0)
        if ((monthlyCondition && timeRange == TimeRange.Month) ||
            (dailyCondition && timeRange == TimeRange.Day) ||
            (timeRange != TimeRange.Month && timeRange != TimeRange.Day))
            canvas.nativeCanvas.drawText(
            label,
            barArea.left + leftOffset,
            barArea.bottom + 65f,
            paint
        )
    }
}