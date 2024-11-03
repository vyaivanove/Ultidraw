package com.vyaivanove.ultidraw.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.ui.graphics.Color

val ColorScheme = darkColorScheme(
    background = Color.Black
)

fun buttonColor(enabled: Boolean = true) = if (enabled) Color.White else Color(0xFF8B8B8B)

fun toolColor(selected: Boolean = false) = if (selected) Color(0xFFA8DB10) else Color.White

val popupBackgroundColor = Color(0xBFB3B3B3)
