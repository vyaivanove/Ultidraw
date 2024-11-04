package com.vyaivanove.ultidraw.editor.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import com.vyaivanove.ultidraw.editor.data.EditorTool

class EditorToolState {
    val tools = mutableStateListOf<EditorTool>(
        EditorTool.pencil(strokeWidth = 10f, color = Color.Red),
        EditorTool.highlighter(strokeWidth = 20f, color = Color.Yellow),
        EditorTool.eraser(strokeWidth = 50f),
    )

    var selectedTool by mutableStateOf(tools.first())
        private set

    fun selectTool(tool: EditorTool) {
        selectedTool = tool
    }

    fun editTool(strokeWidth: Float) = updateTool {
        val style = it.style.let {
            Stroke(
                width = strokeWidth,
                miter = it.miter,
                cap = it.cap,
                join = it.join,
                pathEffect = it.pathEffect
            )
        }
        it.copy(style = style)
    }

    fun changeColor(color: Color) = updateTool {
        it.copy(color = color)
    }

    private inline fun updateTool(update: (EditorTool) -> EditorTool) {
        val index = tools.indexOf(selectedTool)

        val newTool = update(selectedTool)

        tools[index] = newTool
        selectedTool = newTool
    }
}
