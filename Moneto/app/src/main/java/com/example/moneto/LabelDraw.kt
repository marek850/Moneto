package com.example.moneto

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas
import com.example.moneto.ui.theme.Purple80
import com.github.tehras.charts.piechart.utils.toLegacyInt

class LabelDrawer(private val lastDay: Int? = -1) :
    com.github.tehras.charts.bar.renderer.label.LabelDrawer {

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
        canvas.nativeCanvas.drawText(
            label,
            barArea.left + 50f,
            barArea.bottom + 65f,
            paint
        )
    }
}