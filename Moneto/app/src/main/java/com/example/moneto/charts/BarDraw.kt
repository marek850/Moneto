package com.example.moneto.charts

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.example.moneto.data.TimeRange
import com.example.moneto.ui.theme.Background
import com.github.tehras.charts.bar.BarChartData
/**
 * Trieda zodpovedná za vykreslenie stĺpcov na stĺpcových grafoch v aplikácii Moneto.
 * Táto trieda umožňuje prispôsobené vykreslenie stĺpcov na grafe, vrátane ich farby a prispôsobenia pozície na základe časového rozsahu.
 * Táto funkcionalita umožňuje jasnú a efektívnu vizualizáciu dát pre rôzne časové rozsahy.
 *
 * @param timeRange Časový rozsah, podľa ktorého sa určuje posunutie stĺpcov na pravo.
 */
class BarDrawer (timeRange: TimeRange) :
    com.github.tehras.charts.bar.renderer.bar.BarDrawer {
    private val barPaint = Paint().apply {
        this.isAntiAlias = true
    }
    private val rightOffset = when(timeRange) {
        TimeRange.Day -> 10f
        TimeRange.Week -> 24f
        TimeRange.Month -> 6f
        TimeRange.Year -> 18f
    }
    override fun drawBar(
        drawScope: DrawScope,
        canvas: Canvas,
        barArea: Rect,
        bar: BarChartData.Bar
    ) {
        canvas.drawRoundRect(
            barArea.left,
            0f,
            barArea.right + rightOffset,
            barArea.bottom,
            16f,
            16f,
            barPaint.apply {
                color = Background
            },
        )
        canvas.drawRoundRect(
            barArea.left,
            barArea.top,
            barArea.right + rightOffset,
            barArea.bottom,
            16f,
            16f,
            barPaint.apply {
                color = bar.color
            },
        )
    }

}
