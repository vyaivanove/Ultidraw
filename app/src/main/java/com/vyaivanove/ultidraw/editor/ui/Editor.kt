package com.vyaivanove.ultidraw.editor.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vyaivanove.ultidraw.editor.state.EditorState
import com.vyaivanove.ultidraw.editor.viewmodel.EditorViewModel
import com.vyaivanove.ultidraw.ui.theme.Theme
import kotlinx.coroutines.delay

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Editor() {
    val viewModel = viewModel<EditorViewModel>()
    val state = viewModel.state

    if (state is EditorState.View) {
        LaunchedEffect(state) {
            while (true) {
                delay((1000uL / viewModel.settings.frameRate).toLong())
                state.tick()
            }
        }
    }

    Theme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Box {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    EditorToolbar(state = state)
                    Box {
                        EditorCanvas(state = state)

                        if (state is EditorState.Edit && state.popupState.isVisible) {
                            EditorPopup(state = state)
                        }
                    }
                    if (state is EditorState.Edit) {
                        EditorToolPanel(state = state)
                    } else {
                        Spacer(modifier = Modifier.height(32.dp))
                    }
                }

                if (state is EditorState.Edit && state.dialogState.isVisible) {
                    EditorDialog(state = state, settings = viewModel.settings)
                }
            }
        }
    }
}
