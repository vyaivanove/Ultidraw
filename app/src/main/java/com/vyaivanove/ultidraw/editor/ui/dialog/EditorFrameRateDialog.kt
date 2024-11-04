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
fun EditorFrameRateDialog(
    modifier: Modifier = Modifier,
    currentFrameRate: ULong,
    onDismiss: () -> Unit,
    onSetFrameRate: (ULong) -> Unit
) {
    var value by remember { mutableStateOf(TextFieldValue(currentFrameRate.toString())) }
    val frameRate by remember {
        derivedStateOf {
            value.text.toULongOrNull()?.takeUnless { it == 0uL }
        }
    }

    BasicConfirmationDialog(
        modifier = modifier,
        text = stringResource(R.string.dialog_frame_rate_text),
        onDismiss = onDismiss,
        onConfirm = { onSetFrameRate(frameRate!!) },
        isConfirmEnabled = frameRate != null
    ) {
        TextField(
            value = value,
            onValueChange = { value = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = frameRate == null
        )
    }
}
