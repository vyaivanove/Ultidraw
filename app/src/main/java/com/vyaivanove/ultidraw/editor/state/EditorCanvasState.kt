package com.vyaivanove.ultidraw.editor.state

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.geometry.Offset
import com.vyaivanove.ultidraw.editor.data.EditorPath

class EditorCanvasState(val paths: MutableList<EditorPath> = mutableStateListOf<EditorPath>()) {
    private val undonePaths = mutableStateListOf<EditorPath>()

    val isUndoEnabled by derivedStateOf { paths.isNotEmpty() }

    val isRedoEnabled by derivedStateOf { undonePaths.isNotEmpty() }

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

    companion object {
        fun EditorCanvasState.shallowCopy() = EditorCanvasState(
            paths = mutableStateListOf<EditorPath>().apply { addAll(paths) }
        )
    }
}
