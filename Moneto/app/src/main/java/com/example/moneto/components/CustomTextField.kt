@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.moneto.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.moneto.ui.theme.LightText
/**
 * Composable funkcia, ktorá poskytuje štýlované textové pole v aplikácii Moneto.
 * Toto textové pole umožňuje užívateľom vkladať text s možnosťami ako písanie v jednom riadku,
 * vlastné ošetrenie klávesnice a vizuálne transformácie textu.
 * Textové pole môže zobrazovať značky, placeholder text a podporuje rôzne konfigurácie na správu interakcií s užívateľom.
 *
 * @param value Hodnota textu, ktorý sa zobrazuje v textovom poli.
 * @param onValueChange Funkcia, ktorá sa vykoná pri zmene textu v textovom poli.
 * @param enabled Určuje, či je textové pole povolené pre interakciu.
 * @param readOnly Určuje, či je textové pole iba na čítanie.
 * @param textStyle Štýl textu použitý v textovom poli.
 * @param label Kompozitná funkcia, ktorá generuje značku pre textové pole.
 * @param placeholder Kompozitná funkcia, ktorá generuje placeholder text.
 * @param arrangement Usporiadanie obsahu v rámci textového poľa.
 * @param visualTransformation Transformácia aplikovaná na zobrazený text.
 * @param keyboardOptions Možnosti konfigurácie klávesnice.
 * @param keyboardActions Akcie klávesnice, ktoré sa majú vykonať na udalostiach klávesnice.
 * @param isError Označuje, či textové pole zobrazuje chybový stav.
 * @param singleLine Určuje, či textové pole umožňuje vstup v jednom riadku.
 * @param maxLines Maximálny počet riadkov, ktoré textové pole podporuje.
 * @param interactionSource Zdroj interakcie pre textové pole.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    arrangement: Arrangement.Horizontal = Arrangement.Start,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    isError: Boolean = false,
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {

    val textColor = Color.White
    val mergedTextStyle =
        textStyle.merge(TextStyle(color = textColor))

    BasicTextField(value = value,
        onValueChange = onValueChange,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = mergedTextStyle,
        cursorBrush = SolidColor(Color.White),
        visualTransformation = visualTransformation,
        interactionSource = interactionSource,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        decorationBox = @Composable { innerTextField ->
            TextFieldDefaults.DecorationBox(
                value = value,
                innerTextField = innerTextField,
                enabled = enabled,
                singleLine = singleLine,
                visualTransformation = visualTransformation,
                interactionSource = interactionSource,
                isError = isError,
                label = label,
                placeholder = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = arrangement,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        placeholder?.invoke()
                    }
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    cursorColor = LightText,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                ),
                contentPadding = PaddingValues(horizontal = 16.dp),
            )
        })
}