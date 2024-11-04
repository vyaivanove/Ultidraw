package com.vyaivanove.ultidraw.util

import android.graphics.RectF
import androidx.compose.ui.graphics.AndroidPath
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.dp
import kotlin.random.Random
import android.graphics.Path as NativePath

private val DIR = NativePath.Direction.CW
private val DIMENSION_START = 64.dp.value
private val DIMENSION_RANGE = 512.dp.value
private val POSITION_START = 64.dp.value
private val POSITION_RANGE = 512.dp.value
private val OFFSET_RANGE = 32f.dp.value

private fun Random.nextDimension() = nextFloat() * DIMENSION_RANGE + DIMENSION_START
private fun Random.nextPosition() = Random.nextFloat() * POSITION_RANGE + POSITION_START
private fun Random.nextOffset() = (Random.nextFloat() - 0.5f) * OFFSET_RANGE

private val FIGURE_GENERATORS: List<NativePath.(RectF) -> Unit> = listOf(
    { addCircle(it.centerX(), it.centerY(), minOf(it.width(), it.height()), DIR) },
    { addOval(it, DIR) },
    { addRect(it, DIR) }
)

internal inline fun createRandomFrames(count: UInt, onAddFrame: (Path) -> Unit) {
    val figureGenerator = FIGURE_GENERATORS[Random.nextInt(FIGURE_GENERATORS.size)]

    val width = Random.nextDimension()
    val height = Random.nextDimension()

    val centerX = Random.nextPosition()
    val centerY = Random.nextPosition()

    val rectF = RectF(0f, 0f, width, height)
    rectF.offset(centerX, centerY)

    (0u..<count).forEach {
        val path = NativePath()
        path.figureGenerator(rectF)

        onAddFrame(AndroidPath(path))

        val dX = Random.nextOffset()
        val dY = Random.nextOffset()
        rectF.offset(dX, dY)
    }
}
