package com.example.moneto.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.moneto.ui.theme.Destructive
import com.example.moneto.ui.theme.Typography
/**
 * Composable funkcia, ktorá vytvára vlastný riadok v aplikácii Moneto.
 * Tento riadok môže obsahovať textovú značku a umožňuje vloženie ďalšieho obsahu a detailného obsahu prostredníctvom composable funkcií.
 * Riadok podporuje nastavenie deštruktívnej farby pre text a je štýlovo zaoblený.
 *
 * @param modifier Modifikátor, ktorý umožňuje prispôsobenie rozloženia a štýlovania riadku.
 * @param label Textová značka, ktorá sa zobrazí v riadku, ak je poskytnutá.
 * @param isDestructive Ak je true, text v riadku bude mať deštruktívnu farbu, inak bielu.
 * @param detailContent Kompozitná funkcia, ktorá umožňuje vloženie detailného obsahu do riadku.
 * @param content Kompozitná funkcia, ktorá umožňuje vloženie primárneho obsahu do riadku.
 */
@Composable
fun CustomRow(modifier: Modifier = Modifier,
     label: String? = null,
     isDestructive: Boolean = false,
     detailContent: (@Composable RowScope.() -> Unit)? = null,
     content: (@Composable RowScope.() -> Unit)? = null) {
    val textColor = if (isDestructive) Destructive else Color.White

    Row(
        modifier = modifier.fillMaxWidth().clip(RoundedCornerShape(10.dp)),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (label != null) {
            Text(
                text = label,
                style = Typography.labelMedium,
                color = textColor,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
            )
        }
        if (content != null) {
            content()
        }
        if (detailContent != null) {
            detailContent()
        }
    }
}