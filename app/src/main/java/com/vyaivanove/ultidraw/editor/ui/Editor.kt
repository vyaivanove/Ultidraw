package com.vyaivanove.ultidraw.editor.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vyaivanove.ultidraw.editor.state.BackStackController
import com.vyaivanove.ultidraw.editor.state.EditorState
import com.vyaivanove.ultidraw.ui.theme.Theme

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Editor() {
    val state = remember { EditorState() }
    val backStackController by remember { derivedStateOf { BackStackController(state) } }

    Theme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                EditorToolbar(state = state, backStackController = backStackController)
                Box {
                    EditorCanvas(state = state)

                    if (state.popupState.isVisible) {
                        EditorPopup(state = state)
                    }
                }
                EditorToolPanel(state = state)
            }
        }
    }
}
