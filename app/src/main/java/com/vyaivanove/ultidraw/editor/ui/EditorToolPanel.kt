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
import com.vyaivanove.ultidraw.ui.theme.toolColor

@Composable
fun EditorToolPanel(
    modifier: Modifier = Modifier,
    tools: List<EditorTool>,
    selectedTool: EditorTool,
    onSelectTool: (EditorTool) -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val modifier = Modifier.size(32.dp)

        tools.forEach {
            EditorToolPanelIconButton(modifier, icon = it.icon, selected = it === selectedTool) {
                if (it !== selectedTool) {
                    onSelectTool(it)
                }
            }
        }

        EditorToolPanelColorPickerButton(modifier, color = selectedTool.color) {}
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

@Preview(showBackground = true, backgroundColor = 0xFF000000, showSystemUi = true)
@Composable
private fun EditorToolPanelPreview() {
    val tool = EditorTool.Pencil(strokeWidth = 10f, color = Color.Red)
    EditorToolPanel(
        tools = listOf(tool),
        selectedTool = tool,
        onSelectTool = {}
    )
}
