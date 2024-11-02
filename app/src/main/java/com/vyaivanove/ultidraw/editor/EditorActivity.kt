package com.vyaivanove.ultidraw.editor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.vyaivanove.ultidraw.editor.ui.Editor
import com.vyaivanove.ultidraw.ui.theme.SystemBarStyle

class EditorActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(statusBarStyle = SystemBarStyle, navigationBarStyle = SystemBarStyle)
        setContent { Editor() }
    }
}
