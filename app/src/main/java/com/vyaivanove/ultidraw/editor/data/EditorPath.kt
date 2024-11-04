package com.vyaivanove.ultidraw.editor.data

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path

class EditorPath(
    val path: Path, val tool: EditorTool, private var previousPosition: Offset
) {
    constructor(position: Offset, tool: EditorTool) :
            this(Path().apply { moveTo(position.x, position.y) }, tool, position)

    fun quadraticDrag(offset: Offset) {
        val (previousX, previousY) = previousPosition
        val position = previousPosition + offset
        val (x, y) = position

        path.quadraticTo(previousX, previousY, (previousX + x) / 2, (previousY + y) / 2)

        previousPosition = position
    }
}
