package com.vyaivanove.ultidraw.editor.state

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import com.vyaivanove.ultidraw.editor.controller.BackStackController
import com.vyaivanove.ultidraw.editor.data.EditorPath
import com.vyaivanove.ultidraw.editor.state.EditorCanvasState.Companion.shallowCopy
import com.vyaivanove.ultidraw.util.DoublyLinkedList
import com.vyaivanove.ultidraw.util.createRandomFrames

sealed class EditorState {
    abstract val canvasStates: DoublyLinkedList<EditorCanvasState>
    abstract val canvasState: EditorCanvasState
    abstract val onSwitchState: () -> Unit

    class Edit(override val onSwitchState: () -> Unit) : EditorState() {
        // To allow for storage of more than Int.MAX_VALUE entries
        override var canvasStates = DoublyLinkedList<EditorCanvasState>().apply {
            addFirst(EditorCanvasState())
        }
        override val canvasState by derivedStateOf { canvasNode.value }

        private var canvasNode by mutableStateOf(canvasStates.first()!!)

        val previousCanvasState by derivedStateOf { canvasNode.previous()?.value }

        val toolState = EditorToolState()
        val popupState = EditorPopupState()
        val dialogState = EditorDialogState()
        val backStackController = BackStackController(this)

        val hasPreviousCanvas by derivedStateOf { canvasNode.previous() != null }
        val hasNextCanvas by derivedStateOf { canvasNode.next() != null }

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

        fun generateCanvas(frameCount: UInt) {
            createRandomFrames(frameCount) {
                addCanvas()
                canvasState.addPath(
                    EditorPath(
                        path = it,
                        tool = toolState.selectedTool,
                        previousPosition = Offset.Unspecified
                    )
                )
            }
        }

        fun clear() {
            canvasStates.clear()

            canvasNode = canvasStates.addFirst(EditorCanvasState())
        }

        fun previousCanvas() {
            canvasNode = canvasNode.previous()!!
        }

        fun nextCanvas() {
            canvasNode = canvasNode.next()!!
        }
    }

    class View(
        override val canvasStates: DoublyLinkedList<EditorCanvasState>,
        override val onSwitchState: () -> Unit
    ) : EditorState() {
        private var iterator = canvasStates.iterator()

        override var canvasState: EditorCanvasState by mutableStateOf(iterator.next())
            private set

        fun tick() {
            if (!iterator.hasNext()) {
                iterator = canvasStates.iterator()
            }
            canvasState = iterator.next()
        }
    }
}
