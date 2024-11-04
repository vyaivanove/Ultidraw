package com.vyaivanove.ultidraw.editor.data

import androidx.annotation.DrawableRes
import androidx.annotation.FloatRange
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope.Companion.DefaultBlendMode
import androidx.compose.ui.graphics.drawscope.Stroke
import com.vyaivanove.ultidraw.R

data class EditorTool(
    val style: Stroke,
    val color: Color,
    @FloatRange(from = 0.0, to = 1.0) val alpha: Float,
    val blendMode: BlendMode,
    @DrawableRes val icon: Int,
    val eraser: Boolean
) {
    constructor(
        strokeWidth: Float,
        color: Color,
        @FloatRange(from = 0.0, to = 1.0) alpha: Float = 1.0f,
        blendMode: BlendMode = DefaultBlendMode,
        @DrawableRes icon: Int,
        eraser: Boolean = false
    ) : this(
        style = Stroke(width = strokeWidth, cap = StrokeCap.Round, join = StrokeJoin.Round),
        color = color,
        alpha = alpha,
        blendMode = blendMode,
        icon = icon,
        eraser = eraser
    )

    companion object {
        private const val HIGHLIGHTER_ALPHA = 0.5f

        fun pencil(strokeWidth: Float, color: Color) = EditorTool(
            strokeWidth,
            color,
            icon = R.drawable.editor_tool_panel_pencil
        )

        fun highlighter(strokeWidth: Float, color: Color) = EditorTool(
            strokeWidth,
            color,
            alpha = HIGHLIGHTER_ALPHA,
            icon = R.drawable.editor_tool_panel_brush
        )

        fun eraser(strokeWidth: Float) = EditorTool(
            strokeWidth,
            Color.Transparent,
            blendMode = BlendMode.Clear,
            icon = R.drawable.editor_tool_panel_eraser,
            eraser = true
        )
    }
}
