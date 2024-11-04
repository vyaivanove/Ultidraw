package com.vyaivanove.ultidraw.editor.state

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class EditorDialogState {
    enum class States {
        Closed,
        ClearEditor,
        GenerateCanvas,
        FrameRate,
    }

    var state by mutableStateOf(States.Closed)
        private set

    val isVisible by derivedStateOf { state != States.Closed }

    fun showClearEditor() {
        state = States.ClearEditor
    }

    fun showGenerateCanvas() {
        state = States.GenerateCanvas
    }

    fun showFrameRate() {
        state = States.FrameRate
    }

    fun close() {
        state = States.Closed
    }
}
