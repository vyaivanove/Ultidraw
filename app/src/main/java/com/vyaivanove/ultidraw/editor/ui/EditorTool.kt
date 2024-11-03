package com.vyaivanove.ultidraw.editor.ui

import androidx.annotation.DrawableRes
import androidx.annotation.FloatRange
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope.Companion.DefaultBlendMode
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Stroke
import com.vyaivanove.ultidraw.R

sealed class EditorTool(
    val style: DrawStyle,
    val color: Color,
    @FloatRange(from = 0.0, to = 1.0) val alpha: Float,
    val blendMode: BlendMode,
    @DrawableRes val icon: Int
) {
    constructor(
        strokeWidth: Float,
        color: Color,
        @FloatRange(from = 0.0, to = 1.0) alpha: Float = 1.0f,
        blendMode: BlendMode = DefaultBlendMode,
        @DrawableRes icon: Int
    ) : this(
        style = Stroke(width = strokeWidth, cap = StrokeCap.Round, join = StrokeJoin.Round),
        color = color,
        alpha = alpha,
        blendMode = blendMode,
        icon = icon
    )

    class Pencil(strokeWidth: Float, color: Color) : EditorTool(
        strokeWidth,
        color,
        icon = R.drawable.editor_tool_panel_pencil
    )

    class Highlighter(strokeWidth: Float, color: Color) : EditorTool(
        strokeWidth,
        color,
        alpha = HIGHLIGHTER_ALPHA,
        icon = R.drawable.editor_tool_panel_brush
    ) {
        private companion object {
            const val HIGHLIGHTER_ALPHA = 0.5f
        }
    }

    class Eraser(strokeWidth: Float) : EditorTool(
        strokeWidth,
        Color.Transparent,
        blendMode = BlendMode.Clear,
        icon = R.drawable.editor_tool_panel_eraser
    )
}
