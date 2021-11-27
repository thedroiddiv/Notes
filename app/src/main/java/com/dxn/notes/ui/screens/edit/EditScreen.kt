package com.dxn.notes.ui.screens.edit

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Attachment
import androidx.compose.material.icons.rounded.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.dxn.notes.common.models.Note
import com.dxn.notes.ui.components.ColorPicker
import com.dxn.notes.ui.components.HeadingText
import com.dxn.notes.ui.components.TransparentTextField
import java.util.*

@Composable
fun EditScreen(
    navController: NavHostController,
    note: Note? = null
) {
    val editViewModel: EditViewModel = hiltViewModel()
    editViewModel.initValues(note)
    var title by remember { editViewModel.title }
    var text by remember { editViewModel.text }
    var selected by remember { editViewModel.selectedColor }

    Scaffold(
        Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Column {
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "back button")
                }
                HeadingText(modifier = Modifier.weight(1f), text = Date().toString())
                IconButton(onClick = { }) {
                    Icon(imageVector = Icons.Rounded.Attachment, contentDescription = "back button")
                }
                IconButton(onClick = {
                    editViewModel.saveNote(note?.uid) {
                        navController.popBackStack()
                    }
                }) {
                    Icon(imageVector = Icons.Rounded.Save, contentDescription = "save button")
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            TransparentTextField(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = title,
                hint = "Title...",
                textStyle = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold),
                onValueChange = { title = it },
                onFocusChange = {})
            Spacer(modifier = Modifier.height(8.dp))
            TransparentTextField(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .weight(1f),
                text = text,
                textStyle = MaterialTheme.typography.body1,
                hint = "Enter some text...",
                onValueChange = { text = it },
                onFocusChange = {})

            ColorPicker(
                modifier = Modifier.padding(start = 16.dp, bottom = 16.dp, end = 16.dp),
                colors = Note.colors.map { Color(it) },
                selected = selected,
                onSelect = { selected = it })
        }
    }
}