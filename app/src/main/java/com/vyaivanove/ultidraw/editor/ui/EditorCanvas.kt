package com.vyaivanove.ultidraw.editor.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.withSaveLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vyaivanove.ultidraw.R
import com.vyaivanove.ultidraw.editor.state.EditorPath
import com.vyaivanove.ultidraw.editor.state.EditorState
import com.vyaivanove.ultidraw.ui.theme.ColorScheme

@Composable
fun EditorCanvas(
    modifier: Modifier = Modifier,
    state: EditorState
) {
    Box(modifier = modifier.border(1.dp, ColorScheme.background, RoundedCornerShape(20.dp))) {
        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(id = R.drawable.editor_canvas_background),
            contentDescription = null
        )

        if (state is EditorState.Edit) {
            state.previousCanvasState?.let {
                Canvas(
                    modifier = Modifier
                        .matchParentSize()
                        .alpha(0.5f),
                    paths = it.paths
                )
            }
        }

        Canvas(
            paths = state.canvasState.paths,
            modifier = Modifier
                .matchParentSize()
                .run {
                    if (state is EditorState.Edit) {
                        pointerInput(Unit) {
                            detectDragGestures(
                                onDragStart = {
                                    val path =
                                        EditorPath(
                                            position = it,
                                            tool = state.toolState.selectedTool
                                        )
                                    state.canvasState.addPath(path)
                                },
                                onDrag = { _, position ->
                                    state.canvasState.updatePath(position)
                                }
                            )
                        }
                    } else {
                        this
                    }
                }
        )
    }
}

@Composable
private fun Canvas(
    modifier: Modifier = Modifier,
    paths: List<EditorPath>
) {
    Canvas(modifier = modifier) {
        drawContext.canvas.withSaveLayer(size.toRect(), Paint()) {
            val cornersClip = Path().apply {
                addRoundRect(RoundRect(size.toRect(), CornerRadius(20.dp.toPx())))
            }

            clipPath(cornersClip) {
                paths.forEach {
                    with(it.tool) {
                        drawPath(
                            path = it.path,
                            color = color,
                            alpha = alpha,
                            style = style,
                            blendMode = blendMode
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000, showSystemUi = true)
@Composable
private fun EditorCanvasPreview() {
    EditorCanvas(state = EditorState.Edit {})
}
