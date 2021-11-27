package com.dxn.notes.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dxn.notes.ui.theme.NotesTheme

@Composable
fun SearchBar(
    value: String,
    onValueChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    focusManager: FocusManager
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color.Gray.copy(0.2f))
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicTextField(
            modifier = Modifier.weight(0.8f),
            value = value,
            textStyle = MaterialTheme.typography.subtitle2.copy(
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colors.onBackground.copy(0.8f)
            ),
            cursorBrush = SolidColor(MaterialTheme.colors.onBackground.copy(0.8f)),
            onValueChange = onValueChange,
            maxLines = 1,
            singleLine = true,
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearch(value)
                },
                onPrevious = {
                    focusManager.clearFocus()
                }
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            )
        )
        Icon(
            imageVector = Icons.Rounded.Search,
            contentDescription = "",
            tint = MaterialTheme.colors.onBackground.copy(0.5f)
        )
    }
}


@Preview()
@Composable
fun Prev() {
    var text by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    NotesTheme() {
        SearchBar(
            value = text,
            onValueChange = { text = it },
            onSearch = {

            },
            focusManager = focusManager
        )
    }
}