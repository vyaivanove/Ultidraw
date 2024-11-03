package com.vyaivanove.ultidraw.editor.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vyaivanove.myapplication.ui.theme.Theme

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Editor() {
    Theme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(top = 8.dp)
            ) {
                EditorToolbar(modifier = Modifier.padding(horizontal = 16.dp))
                Spacer(modifier = Modifier.weight(1f))
                EditorToolPanel(modifier = Modifier.padding(horizontal = 16.dp))
            }
        }
    }
}