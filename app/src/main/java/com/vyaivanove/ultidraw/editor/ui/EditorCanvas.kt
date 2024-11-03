package com.vyaivanove.ultidraw.editor.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vyaivanove.ultidraw.R
import com.vyaivanove.ultidraw.ui.theme.ColorScheme

internal data class EditorPath(
    val path: Path, val properties: Properties, private var previousPosition: Offset
) {
    constructor(position: Offset) : this(
        Path().apply { moveTo(position.x, position.y) }, Properties(), position
    )

    fun quadraticDrag(offset: Offset) {
        val (previousX, previousY) = previousPosition
        val position = previousPosition + offset
        val (x, y) = position

        path.quadraticTo(previousX, previousY, (previousX + x) / 2, (previousY + y) / 2)

        previousPosition = position
    }

    class Properties(
        val style: DrawStyle = Stroke(width = 10f, cap = StrokeCap.Round, join = StrokeJoin.Round),
        val color: Color = Color.Red,
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF000000, showSystemUi = true)
@Composable
fun EditorCanvas(modifier: Modifier = Modifier) {
    val paths = remember { mutableStateListOf<EditorPath>() }

    Box(modifier = modifier.border(1.dp, ColorScheme.background, RoundedCornerShape(20.dp))) {
        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(id = R.drawable.editor_canvas_background),
            contentDescription = null
        )

        Canvas(
            modifier = Modifier.matchParentSize(),
            paths = paths,
            onDragStart = { position -> paths.add(EditorPath(position)) },
            onDrag = { offset ->
                val last = paths.removeAt(paths.lastIndex)
                last.quadraticDrag(offset)
                paths.add(last)
            }
        )
    }
}

@Composable
private fun Canvas(
    modifier: Modifier = Modifier,
    paths: List<EditorPath>,
    onDragStart: (position: Offset) -> Unit,
    onDrag: (offset: Offset) -> Unit
) {
    Canvas(
        modifier = modifier.pointerInput(Unit) {
            detectDragGestures(onDragStart = onDragStart) { _, position ->
                onDrag(position)
            }
        }
    ) {
        val cornersClip = Path().apply {
            addRoundRect(RoundRect(size.toRect(), CornerRadius(20.dp.toPx())))
        }

        clipPath(cornersClip) {
            paths.forEach { (path, properties) ->
                with(properties) {
                    drawPath(path = path, color = color, style = style)
                }
            }
        }
    }
}
