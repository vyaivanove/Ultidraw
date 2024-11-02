package com.vyaivanove.ultidraw.editor.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vyaivanove.myapplication.ui.theme.buttonColor
import com.vyaivanove.ultidraw.R

@Preview(showBackground = true, backgroundColor = 0xFF000000, showSystemUi = true)
@Composable
fun EditorToolbar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            val modifier = Modifier.size(24.dp)

            ToolbarIconButton(modifier, icon = R.drawable.editor_toolbar_undo) {}
            ToolbarIconButton(modifier, icon = R.drawable.editor_toolbar_redo) {}
        }
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            val modifier = Modifier.size(32.dp)

            ToolbarIconButton(modifier, icon = R.drawable.editor_toolbar_delete) {}
            ToolbarIconButton(modifier, icon = R.drawable.editor_toolbar_add) {}
            ToolbarIconButton(modifier, icon = R.drawable.editor_toolbar_layers) {}
        }
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            val modifier = Modifier.size(32.dp)

            ToolbarIconButton(modifier, icon = R.drawable.editor_toolbar_stop) {}
            ToolbarIconButton(modifier, icon = R.drawable.editor_toolbar_play) {}
        }
    }
}

@Composable
private fun ToolbarIconButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    @DrawableRes icon: Int,
    onClick: () -> Unit
) {
    Icon(
        modifier = modifier.clickable(
            enabled = enabled,
            onClick = onClick,
            interactionSource = remember { MutableInteractionSource() },
            indication = ripple(bounded = false, radius = 24.dp)
        ),
        painter = painterResource(id = icon),
        tint = buttonColor(enabled = enabled),
        contentDescription = null,
    )
}
