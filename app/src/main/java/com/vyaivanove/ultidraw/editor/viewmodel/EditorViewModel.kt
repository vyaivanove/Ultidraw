package com.vyaivanove.ultidraw.editor.viewmodel

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.vyaivanove.ultidraw.editor.data.EditorSettings
import com.vyaivanove.ultidraw.editor.state.EditorState

class EditorViewModel : ViewModel() {
    private val editState = EditorState.Edit {
        viewState = createViewState()
    }
    private var viewState by mutableStateOf<EditorState.View?>(null)

    private fun createViewState(): EditorState.View = EditorState.View(editState.canvasStates) {
        viewState = null
    }

    val state by derivedStateOf { viewState ?: editState }

    val settings = EditorSettings()
}
