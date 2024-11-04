package com.vyaivanove.ultidraw.editor.ui.dialog

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.vyaivanove.ultidraw.R

@Composable
fun EditorClearDialog(modifier: Modifier = Modifier, onDismiss: () -> Unit, onClear: () -> Unit) {
    BasicConfirmationDialog(
        modifier = modifier,
        text = stringResource(R.string.dialog_clear_editor_text),
        onDismiss = onDismiss,
        onConfirm = onClear
    )
}
