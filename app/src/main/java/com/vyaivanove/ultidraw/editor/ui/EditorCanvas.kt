package com.vyaivanove.ultidraw.editor.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.vyaivanove.ultidraw.R


@Preview(showBackground = true, backgroundColor = 0xFF000000, showSystemUi = true)
@Composable
fun EditorCanvas(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.fillMaxWidth(),
        painter = painterResource(id = R.drawable.editor_canvas_background),
        contentDescription = null
    )
}
