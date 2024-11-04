package com.vyaivanove.ultidraw.editor.state

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.vyaivanove.ultidraw.editor.state.EditorCanvasState.Companion.shallowCopy
import com.vyaivanove.ultidraw.util.DoublyLinkedList
import com.vyaivanove.ultidraw.util.DoublyLinkedList.Node

sealed class EditorState() {
    abstract val canvasStates: Collection<EditorCanvasState>
    abstract val canvasState: EditorCanvasState
    abstract val onSwitchState: () -> Unit

    class Edit(override val onSwitchState: () -> Unit) : EditorState() {
        // To allow for storage of more than Int.MAX_VALUE entries
        override var canvasStates = DoublyLinkedList<EditorCanvasState>().apply {
            addFirst(EditorCanvasState())
        }
        override val canvasState by derivedStateOf { canvasNode.value }

        private var canvasNode by mutableStateOf<Node<EditorCanvasState>>(canvasStates.first()!!)

        val previousCanvasState by derivedStateOf { canvasNode.previous()?.value }

        val toolState = EditorToolState()
        val popupState = EditorPopupState()
        val backStackController = BackStackController(this)

        fun addCanvas() {
            canvasNode = canvasStates.addAfter(canvasNode, EditorCanvasState())
        }

        fun duplicateCanvas() {
            canvasNode = canvasStates.addAfter(canvasNode, canvasNode.value.shallowCopy())
        }

        fun removeCanvas() {
            canvasNode =
                canvasNode.previous() ?: canvasStates.addBefore(canvasNode, EditorCanvasState())

            canvasStates.remove(canvasNode.next()!!)
        }
    }

    class View(
        override val canvasStates: Collection<EditorCanvasState>,
        override val onSwitchState: () -> Unit
    ) : EditorState() {
        private var iterator = canvasStates.iterator()

        override var canvasState: EditorCanvasState by mutableStateOf<EditorCanvasState>(iterator.next())
            private set

        fun tick() {
            if (!iterator.hasNext()) {
                iterator = canvasStates.iterator()
            }
            canvasState = iterator.next()
        }
    }
}
