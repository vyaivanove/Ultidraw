package com.vyaivanove.ultidraw.editor.state

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.vyaivanove.ultidraw.util.DoublyLinkedList
import com.vyaivanove.ultidraw.util.DoublyLinkedList.Node

class EditorState {
    // To allow for storage of more than Int.MAX_VALUE entries
    private var canvasStates = DoublyLinkedList<EditorCanvasState>().apply {
        addFirst(EditorCanvasState())
    }
    private var canvasNode by mutableStateOf<Node<EditorCanvasState>>(canvasStates.first()!!)

    val previousCanvasState by derivedStateOf { canvasNode.previous()?.value }
    val canvasState by derivedStateOf { canvasNode.value }

    val toolState = EditorToolState()
    val popupState = EditorPopupState()

    fun addCanvas() {
        canvasNode = canvasStates.addAfter(canvasNode, EditorCanvasState())
    }

    fun removeCanvas() {
        canvasNode =
            canvasNode.previous() ?: canvasStates.addBefore(canvasNode, EditorCanvasState())

        canvasStates.remove(canvasNode.next()!!)
    }
}
