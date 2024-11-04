package com.vyaivanove.ultidraw.editor.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
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
import com.vyaivanove.ultidraw.R
import com.vyaivanove.ultidraw.editor.controller.BackStackController
import com.vyaivanove.ultidraw.editor.state.EditorState
import com.vyaivanove.ultidraw.ui.theme.buttonColor
import com.vyaivanove.ultidraw.util.DoublyLinkedList

@Composable
fun EditorToolbar(
    modifier: Modifier = Modifier,
    state: EditorState
) {
    val horizontalArrangement = when (state) {
        is EditorState.Edit -> Arrangement.SpaceBetween
        is EditorState.View -> Arrangement.End
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (state is EditorState.Edit) {
            EditorToolbarBackStackGroup(state.backStackController)
            EditorToolbarCanvasGroup(state)
        }
        EditorToolbarPlaybackGroup(state)
    }

}

@Composable
private fun EditorToolbarBackStackGroup(backStackController: BackStackController) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        val modifier = Modifier.size(24.dp)

        ToolbarIconButton(
            modifier,
            enabled = backStackController.isUndoEnabled,
            icon = R.drawable.editor_toolbar_undo,
            onClick = backStackController::undo
        )
        ToolbarIconButton(
            modifier,
            enabled = backStackController.isRedoEnabled,
            icon = R.drawable.editor_toolbar_redo,
            onClick = backStackController::redo
        )
    }
}

@Composable
private fun EditorToolbarCanvasGroup(state: EditorState.Edit) {
    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        val modifier = Modifier.size(32.dp)

        ToolbarIconButton(
            modifier,
            icon = R.drawable.editor_toolbar_delete,
            onClick = state::removeCanvas,
            onLongClick = state.dialogState::showClearEditor
        )
        ToolbarIconButton(
            modifier,
            icon = R.drawable.editor_toolbar_add,
            onClick = state::addCanvas,
            onLongClick = state.dialogState::showGenerateCanvas
        )
        ToolbarIconButton(
            modifier,
            icon = R.drawable.editor_toolbar_duplicate,
            onClick = state::duplicateCanvas
        )
        ToolbarIconButton(
            modifier,
            icon = R.drawable.editor_toolbar_layers,
            onClick = state.popupState::showFrameSelector
        )
    }
}

@Composable
private fun EditorToolbarPlaybackGroup(state: EditorState) {
    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        val modifier = Modifier.size(32.dp)

        ToolbarIconButton(
            modifier,
            icon = R.drawable.editor_toolbar_stop,
            enabled = state is EditorState.View,
            onClick = state.onSwitchState
        )
        ToolbarIconButton(
            modifier,
            icon = R.drawable.editor_toolbar_play,
            enabled = state is EditorState.Edit,
            onClick = state.onSwitchState,
            onLongClick = {
                if (state is EditorState.Edit) {
                    state.dialogState.showFrameRate()
                }
            }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ToolbarIconButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    @DrawableRes icon: Int,
    onLongClick: () -> Unit = {},
    onClick: () -> Unit = {}
) {
    Icon(
        modifier = modifier.combinedClickable(
            enabled = enabled,
            onClick = onClick,
            onLongClick = onLongClick,
            interactionSource = remember { MutableInteractionSource() },
            indication = ripple(bounded = false, radius = 24.dp)
        ),
        painter = painterResource(id = icon),
        tint = buttonColor(enabled = enabled),
        contentDescription = null,
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF000000, showSystemUi = true)
@Composable
private fun EditorToolbarEditPreview() {
    EditorToolbar(state = EditorState.Edit {})
}

@Preview(showBackground = true, backgroundColor = 0xFF000000, showSystemUi = true)
@Composable
private fun EditorToolbarViewPreview() {
    EditorToolbar(state = EditorState.View(DoublyLinkedList()) {})
}
