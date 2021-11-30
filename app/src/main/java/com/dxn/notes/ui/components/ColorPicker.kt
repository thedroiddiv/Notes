package com.dxn.notes.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dxn.notes.ui.theme.NotesTheme

@Composable
fun ColorPicker(
    modifier: Modifier = Modifier,
    colors: List<Color>,
    selected: Int,
    onSelect: (Int) -> Unit
) {
        LazyRow(
            Modifier
                .padding(16.dp)
                .fillMaxWidth()) {
            itemsIndexed(colors) { index, color ->
                Box(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .width(24.dp)
                        .height(24.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .border(
                            2.dp,
                            if (index == selected) Color.Black else Color.Transparent,
                            CircleShape
                        )
//                        .clickable { onSelect(index) }
                ){
                    Box(modifier = Modifier.background(color).fillMaxSize().clickable { onSelect(index) })
                }

            }
//        }
    }
}
