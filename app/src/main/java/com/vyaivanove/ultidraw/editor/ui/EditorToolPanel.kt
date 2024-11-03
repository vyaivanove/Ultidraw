package com.vyaivanove.ultidraw.editor.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vyaivanove.ultidraw.R
import com.vyaivanove.ultidraw.ui.theme.toolColor

@Preview(showBackground = true, backgroundColor = 0xFF000000, showSystemUi = true)
@Composable
fun EditorToolPanel(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val modifier = Modifier.size(32.dp)

        EditorToolPanelIconButton(modifier, icon = R.drawable.editor_tool_panel_pencil) {}
        EditorToolPanelIconButton(modifier, icon = R.drawable.editor_tool_panel_brush) {}
        EditorToolPanelIconButton(modifier, icon = R.drawable.editor_tool_panel_eraser) {}
        EditorToolPanelColorPickerButton(modifier, color = Color.White) {}
    }
}

@Composable
private fun EditorToolPanelIconButton(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    selected: Boolean = false,
    onClick: () -> Unit
) {
    Icon(
        modifier = modifier.clickable(
            onClick = onClick,
            interactionSource = remember { MutableInteractionSource() },
            indication = ripple(bounded = false, radius = 24.dp)
        ),
        painter = painterResource(id = icon),
        tint = toolColor(selected = selected),
        contentDescription = null,
    )
}

@Composable
private fun EditorToolPanelColorPickerButton(
    modifier: Modifier = Modifier,
    color: Color,
    selected: Boolean = false,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clickable(
                onClick = onClick,
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(bounded = false, radius = 24.dp)
            )
            .clip(CircleShape)
            .background(color)
            .run { if (selected) border(2.dp, toolColor(selected = true), CircleShape) else this }
    )
}
