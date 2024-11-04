package com.vyaivanove.ultidraw.editor.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.vyaivanove.ultidraw.editor.state.EditorPopupState.States.Closed
import com.vyaivanove.ultidraw.editor.state.EditorPopupState.States.ColorPicker
import com.vyaivanove.ultidraw.editor.state.EditorPopupState.States.FrameSelector
import com.vyaivanove.ultidraw.editor.state.EditorPopupState.States.ToolEditor
import com.vyaivanove.ultidraw.editor.state.EditorState
import com.vyaivanove.ultidraw.editor.ui.popup.EditorColorPicker
import com.vyaivanove.ultidraw.editor.ui.popup.EditorFrameSelector
import com.vyaivanove.ultidraw.editor.ui.popup.EditorToolEditor
import com.vyaivanove.ultidraw.ui.theme.popupBackgroundColor

@Composable
fun EditorPopup(modifier: Modifier = Modifier, state: EditorState.Edit) {
    val popupModifier = modifier
        .background(color = popupBackgroundColor, shape = RoundedCornerShape(4.dp))
        .padding(16.dp)

    Popup(
        alignment = Alignment.BottomCenter,
        onDismissRequest = state.popupState::close,
        properties = PopupProperties(
            focusable = true,
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {
        when (state.popupState.state) {
            ColorPicker -> EditorColorPicker(modifier = popupModifier) { color ->
                state.toolState.changeColor(color)
                state.popupState.close()
            }

            ToolEditor -> EditorToolEditor(modifier = popupModifier)
            FrameSelector -> EditorFrameSelector(modifier = popupModifier, state = state)

            Closed -> Unit // Unreachable
        }
    }
}
