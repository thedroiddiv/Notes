package com.dxn.notes.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dxn.notes.common.models.Note

@Composable
fun NoteBox(
    modifier: Modifier = Modifier,
    note: Note,
    onClick : () -> Unit
) {
    Column(
        modifier
            .padding(horizontal = 8.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color(note.color))
            .clickable { onClick() }
            .padding(12.dp)
    ) {
        HeadingText(text = note.title)
        BodyText(text = note.text, maxLines = 10)
    }
}