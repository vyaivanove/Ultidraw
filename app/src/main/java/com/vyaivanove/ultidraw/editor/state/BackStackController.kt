package com.vyaivanove.ultidraw.editor.state

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue

class BackStackController(
    private val editorState: EditorState
) {
    val isUndoEnabled by derivedStateOf { editorState.canvasState.isUndoEnabled }

    val isRedoEnabled by derivedStateOf { editorState.canvasState.isRedoEnabled }

    fun undo() {
        editorState.canvasState.undo()
    }

    fun redo() {
        editorState.canvasState.redo()
    }
}
