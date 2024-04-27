package com.example.moneto.charts

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.github.tehras.charts.piechart.PieChartData
import com.github.tehras.charts.piechart.renderer.SliceDrawer

class SliceDrawer : SliceDrawer{
    private val barPaint = Paint().apply {
        this.isAntiAlias = true
    }
    override fun drawSlice(
        drawScope: DrawScope,
        canvas: Canvas,
        area: Size,
        startAngle: Float,
        sweepAngle: Float,
        slice: PieChartData.Slice
    ) {
        val diameter = minOf(area.width, area.height)
        val left = (area.width - diameter) / 2
        val top = (area.height - diameter) / 2
        val right = left + diameter
        val bottom = top + diameter
        android.graphics.RectF(left, top, right, bottom)

        // Set the color for the slice
        barPaint.color = slice.color

        // Draw the arc for the slice


    }
}