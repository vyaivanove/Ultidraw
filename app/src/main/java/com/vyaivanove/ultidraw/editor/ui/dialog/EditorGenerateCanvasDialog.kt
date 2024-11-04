package com.vyaivanove.ultidraw.editor.ui.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.vyaivanove.ultidraw.R

@Composable
fun EditorGenerateCanvasDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onGenerate: (UInt) -> Unit
) {
    var value by remember { mutableStateOf(TextFieldValue("1")) }
    val number by remember { derivedStateOf { value.text.toUIntOrNull() } }

    Column(modifier = modifier) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = stringResource(R.string.dialog_generate_canvas_text)
        )

        TextField(
            value = value,
            onValueChange = { value = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = number == null
        )

        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End)
        ) {
            TextButton(onClick = onDismiss) {
                Text(stringResource(id = android.R.string.cancel))
            }
            TextButton(
                enabled = number != null,
                onClick = { onGenerate(number!!) }
            ) {
                Text(stringResource(id = android.R.string.ok))
            }
        }
    }
}
