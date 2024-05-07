package com.example.moneto.charts

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.github.tehras.charts.piechart.PieChartData
import com.github.tehras.charts.piechart.renderer.SliceDrawer
/**
 * Trieda pre vykresľovanie výsekov koláčového grafu v aplikácii Moneto.
 * Táto trieda definuje spôsob vykreslenia jednotlivých výsekov koláčového grafu, vrátane ich farby a tvaru.
 * Používa sa v rámci grafických komponentov, kde je potrebné vizualizovať dáta vo forme koláčového grafu.
 */
class SliceDrawer : SliceDrawer{
    private val barPaint = Paint().apply {
        this.isAntiAlias = true
    }
    /**
     * Funkcia vykreslujúca jednotlivý výsek koláčového grafu.
     * @param drawScope Rozsah kreslenia, poskytuje kontext a nástroje pre kreslenie.
     * @param canvas Plátno, na ktorom sa vykonáva kreslenie.
     * @param area Rozmery oblasti, kde sa výsek vykreslí.
     * @param startAngle Začiatočný uhol výseku v stupňoch.
     * @param sweepAngle Rozsah uhla výseku v stupňoch.
     * @param slice Dáta výseku, ktoré obsahujú hodnotu a farbu.
     */
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