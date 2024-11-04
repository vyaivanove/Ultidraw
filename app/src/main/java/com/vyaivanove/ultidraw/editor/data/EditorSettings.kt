package com.vyaivanove.ultidraw.editor.data

class EditorSettings {
    var frameRate: ULong = 10u
        private set

    fun setFrameRate(frameRate: ULong) {
        this.frameRate = frameRate
    }
}
