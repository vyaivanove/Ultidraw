package com.vyaivanove.ultidraw.editor.ui.dialog

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import com.vyaivanove.ultidraw.R

@Composable
fun EditorGenerateCanvasDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onGenerate: (UInt) -> Unit
) {
    var value by remember { mutableStateOf(TextFieldValue("1")) }
    val count by remember { derivedStateOf { value.text.toUIntOrNull() } }

    BasicConfirmationDialog(
        modifier = modifier,
        text = stringResource(R.string.dialog_generate_canvas_text),
        onDismiss = onDismiss,
        onConfirm = { onGenerate(count!!) },
        isConfirmEnabled = count != null
    ) {
        TextField(
            value = value,
            onValueChange = { value = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = count == null
        )
    }
}
