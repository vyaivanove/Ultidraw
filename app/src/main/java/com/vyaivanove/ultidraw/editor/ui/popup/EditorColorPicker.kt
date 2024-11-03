package com.vyaivanove.ultidraw.editor.ui.popup

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun EditorColorPicker(modifier: Modifier = Modifier, onColorChanged: (Color) -> Unit) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        listOf(Color.White, Color.Red, Color.Black, Color.Blue, Color.Yellow).forEach { color ->
            EditorColorPickerButton(color = color) {
                onColorChanged(color)
            }
        }
    }
}

@Composable
private fun EditorColorPickerButton(
    modifier: Modifier = Modifier,
    color: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .size(24.dp)
            .clickable(
                onClick = onClick,
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(bounded = false, radius = 24.dp, color = Color.White)
            )
            .clip(CircleShape)
            .background(color)
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF000000, showSystemUi = true)
@Composable
private fun EditorColorPickerPreview() {
    EditorColorPicker {}
}
