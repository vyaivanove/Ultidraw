package com.vyaivanove.ultidraw.editor.ui.popup

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.vyaivanove.ultidraw.R
import com.vyaivanove.ultidraw.editor.state.EditorState
import com.vyaivanove.ultidraw.ui.theme.buttonColor

@Composable
fun EditorFrameSelector(
    modifier: Modifier = Modifier,
    state: EditorState.Edit
) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        val iconModifier = Modifier.size(32.dp)

        IconButton(
            modifier = iconModifier,
            enabled = state.hasPreviousCanvas,
            icon = R.drawable.editor_toolbar_undo,
            onClick = state::previousCanvas
        )
        IconButton(
            modifier = iconModifier,
            enabled = state.hasNextCanvas,
            icon = R.drawable.editor_toolbar_redo,
            onClick = state::nextCanvas
        )
    }
}

@Composable
private fun IconButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    @DrawableRes icon: Int,
    onClick: () -> Unit = {}
) {
    Icon(
        modifier = modifier.clickable(
            enabled = enabled,
            onClick = onClick,
            interactionSource = remember { MutableInteractionSource() },
            indication = ripple(bounded = false, radius = 32.dp)
        ),
        painter = painterResource(id = icon),
        tint = buttonColor(enabled = enabled),
        contentDescription = null,
    )
}
