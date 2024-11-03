package com.vyaivanove.ultidraw.editor.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.vyaivanove.ultidraw.ui.theme.Theme

enum class PopupState {
    Closed,
    ColorPicker,
    ToolEditor,
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Editor() {
    val tools = remember {
        mutableStateListOf<EditorTool>(
            EditorTool.Pencil(strokeWidth = 10f, color = Color.Red),
            EditorTool.Highlighter(strokeWidth = 20f, color = Color.Yellow),
            EditorTool.Eraser(strokeWidth = 50f),
        )
    }

    var tool by remember { mutableStateOf(tools.first()) }

    val paths = remember { mutableStateListOf<EditorPath>() }
    val undonePaths = remember { mutableStateListOf<EditorPath>() }

    var popupState: PopupState by remember { mutableStateOf(PopupState.Closed) }

    Theme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                EditorToolbar(
                    undoEnabled = paths.isNotEmpty(),
                    redoEnabled = undonePaths.isNotEmpty(),
                    onUndo = { undonePaths.add(paths.removeAt(paths.lastIndex)) },
                    onRedo = { paths.add(undonePaths.removeAt(undonePaths.lastIndex)) }
                )
                Box {
                    EditorCanvas(
                        paths = paths,
                        onAddPath = {
                            undonePaths.clear()
                            paths.add(EditorPath(it, tool))
                        },
                        onUpdatePath = {
                            val path = paths.removeAt(paths.lastIndex)
                            path.quadraticDrag(it)
                            paths.add(path)
                        }
                    )
                    if (popupState != PopupState.Closed) {
                        Popup(
                            alignment = Alignment.BottomCenter,
                            onDismissRequest = { popupState = PopupState.Closed },
                            properties = PopupProperties(
                                focusable = true,
                                dismissOnBackPress = true,
                                dismissOnClickOutside = true
                            )
                        ) {
                            when (popupState) {
                                PopupState.ColorPicker -> EditorColorPicker()
                                PopupState.ToolEditor -> EditorToolEditor()
                                else -> Unit // Unreachable
                            }
                        }
                    }
                }
                EditorToolPanel(
                    tools = tools,
                    selectedTool = tool,
                    onSelectTool = { tool = it },
                    onChangeTool = {
                        popupState = PopupState.ToolEditor
                    },
                    onChangeColor = {
                        popupState = PopupState.ColorPicker
                    }
                )
            }
        }
    }
}
