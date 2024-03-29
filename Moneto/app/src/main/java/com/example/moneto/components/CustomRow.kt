package com.example.moneto.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.moneto.ui.theme.Destructive
import com.example.moneto.ui.theme.Typography

@Composable
fun CustomRow(modifier: Modifier = Modifier,
     label: String? = null,
     isDestructive: Boolean = false,
     detailContent: (@Composable RowScope.() -> Unit)? = null,
     content: (@Composable RowScope.() -> Unit)? = null) {
    val textColor = if (isDestructive) Destructive else Color.White

    Row(
        modifier = modifier.fillMaxWidth(),
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