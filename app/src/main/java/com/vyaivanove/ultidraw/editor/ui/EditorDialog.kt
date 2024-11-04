package com.vyaivanove.ultidraw.editor.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.vyaivanove.ultidraw.editor.state.EditorDialogState.States.*
import com.vyaivanove.ultidraw.editor.state.EditorState
import com.vyaivanove.ultidraw.editor.ui.dialog.EditorClearDialog
import com.vyaivanove.ultidraw.editor.ui.dialog.EditorGenerateCanvasDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditorDialog(modifier: Modifier = Modifier, state: EditorState.Edit) {
    BasicAlertDialog(
        onDismissRequest = state.dialogState::close,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {
        Card(
            modifier = modifier,
            shape = RoundedCornerShape(16.dp)
        ) {
            when (state.dialogState.state) {
                ClearEditor -> EditorClearDialog(
                    modifier = Modifier.padding(16.dp),
                    onDismiss = state.dialogState::close
                ) {
                    state.clear()
                    state.dialogState.close()
                }

                GenerateCanvas -> EditorGenerateCanvasDialog(
                    modifier = Modifier.padding(16.dp),
                    onDismiss = state.dialogState::close
                ) {
                    state.generateCanvas(it)
                    state.dialogState.close()
                }

                Closed -> Unit // Unreachable
            }
        }
    }
}
