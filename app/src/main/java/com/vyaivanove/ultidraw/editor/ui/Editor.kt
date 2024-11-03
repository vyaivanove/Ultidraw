package com.vyaivanove.ultidraw.editor.ui

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vyaivanove.ultidraw.ui.theme.Theme

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

    Theme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(top = 8.dp)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                EditorToolbar(
                    undoEnabled = paths.isNotEmpty(),
                    redoEnabled = undonePaths.isNotEmpty(),
                    onUndo = { undonePaths.add(paths.removeAt(paths.lastIndex)) },
                    onRedo = { paths.add(undonePaths.removeAt(undonePaths.lastIndex)) }
                )
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
                EditorToolPanel(
                    tools = tools,
                    selectedTool = tool,
                    onSelectTool = { tool = it }
                )
            }
        }
    }
}
