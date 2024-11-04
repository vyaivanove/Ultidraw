package com.vyaivanove.ultidraw.editor.state

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class EditorPopupState {
    enum class States {
        Closed,
        ColorPicker,
        ToolEditor,
    }

    var state by mutableStateOf(States.Closed)
        private set

    val isVisible by derivedStateOf { state != States.Closed }

    val isShowingColorPicker
        get() = state == States.ColorPicker

    fun showColorPicker() {
        state = States.ColorPicker
    }

    fun showToolEditor() {
        state = States.ToolEditor
    }

    fun close() {
        state = States.Closed
    }
}
