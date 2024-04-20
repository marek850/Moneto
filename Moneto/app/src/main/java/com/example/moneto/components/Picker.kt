package com.example.moneto.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.moneto.ui.theme.LightPurple
import com.example.moneto.ui.theme.Purple80
import com.example.moneto.ui.theme.Typography

@Composable
fun Picker(label: String, onClick: () -> Unit) {
    Surface(
        color = LightPurple,
        shape = RoundedCornerShape(10.dp),
        onClick = onClick,
        modifier = Modifier
            .clip(
                RoundedCornerShape(10.dp)
            )
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 3.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(label, style = Typography.labelLarge, color = Purple80)
            Icon(
                Icons.Default.KeyboardArrowDown ,contentDescription = null,
                tint = Purple80,
                modifier = Modifier.size(23.dp).padding(start = 10.dp))
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun Preview() {

        Picker("this week", onClick = {})

}