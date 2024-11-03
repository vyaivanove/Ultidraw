package com.vyaivanove.ultidraw.editor.state

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.geometry.Offset

class EditorCanvasState {
    val paths = mutableStateListOf<EditorPath>()
    private val undonePaths = mutableStateListOf<EditorPath>()

    val isUndoEnabled
        get() = paths.isNotEmpty()

    val isRedoEnabled
        get() = undonePaths.isNotEmpty()

    fun undo() {
        undonePaths.add(paths.removeAt(paths.lastIndex))
    }

    fun redo() {
        paths.add(undonePaths.removeAt(undonePaths.lastIndex))
    }

    fun addPath(path: EditorPath) {
        undonePaths.clear()
        paths.add(path)
    }

    fun updatePath(offset: Offset) {
        val path = paths.removeAt(paths.lastIndex)
        path.quadraticDrag(offset)
        paths.add(path)
    }
}
