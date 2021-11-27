package com.dxn.notes.common.models

import androidx.compose.ui.graphics.toArgb
import com.dxn.notes.ui.theme.*

data class Note(
    val title: String = "",
    val text: String = "",
    val color: Int = colors[0],
    val timeStamp: Long = System.currentTimeMillis(),
    val uid: String = System.currentTimeMillis().toString()
) {
    companion object {
        val colors = listOf(
            NoteRed.toArgb(),
            NoteBlue.toArgb(),
            NoteYellow.toArgb(),
            NoteGreen.toArgb(),
            NoteViolet.toArgb(),
            NotePink.toArgb(),
            NoteOrange.toArgb()
        )
    }
}