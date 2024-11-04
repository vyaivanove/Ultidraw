package com.vyaivanove.ultidraw.editor.state

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class EditorStateHolder {
    private val editState = EditorState.Edit {
        viewState = createViewState()
    }
    private var viewState by mutableStateOf<EditorState.View?>(null)

    private fun createViewState(): EditorState.View = EditorState.View(editState.canvasStates) {
        viewState = null
    }

    val state by derivedStateOf { viewState ?: editState }
}
