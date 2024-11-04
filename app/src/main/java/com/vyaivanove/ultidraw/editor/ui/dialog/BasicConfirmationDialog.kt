package com.vyaivanove.ultidraw.editor.ui.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun BasicConfirmationDialog(
    modifier: Modifier,
    text: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    isConfirmEnabled: Boolean = true,
    content: @Composable () -> Unit = {}
) {
    Column(modifier = modifier) {
        Text(modifier = Modifier.padding(8.dp), text = text)
        content()
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End)
        ) {
            TextButton(onClick = onDismiss) {
                Text(stringResource(id = android.R.string.cancel))
            }
            TextButton(enabled = isConfirmEnabled, onClick = onConfirm) {
                Text(stringResource(id = android.R.string.ok))
            }
        }
    }
}
