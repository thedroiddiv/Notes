package com.dxn.notes.ui.screens.home.notes

import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.dxn.notes.R
import com.dxn.notes.ui.components.BodyText
import com.dxn.notes.ui.components.HeadingText
import com.dxn.notes.ui.components.NoteBox
import com.dxn.notes.ui.navigation.Screens
import com.google.gson.Gson


@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun Notes(
    viewModel: NotesViewModel,
    filter: String = "",
    navController: NavHostController
) {
    val notes by remember { viewModel.notes }

    Spacer(modifier = Modifier.height(16.dp))
    if (notes.isEmpty()) {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(48.dp),
                painter = painterResource(id = R.drawable.ic_illustration),
                contentDescription = "",
                contentScale = ContentScale.FillWidth
            )
            HeadingText(text = "It's Empty !")
            BodyText(
                text = "Hmm.. looks like you don't \nhave any notes",
                textAlign = TextAlign.Center
            )
        }
    }
    LazyVerticalGrid(cells = GridCells.Fixed(2)) {
        items(notes.filter { note ->
            note.title.contains(filter, true) || note.text.contains(
                filter,
                true
            )
        }) { note ->
            val dismissState = rememberDismissState(
                initialValue = DismissValue.Default,
                confirmStateChange = {
                    viewModel.removeNote(note.uid)
                    it != DismissValue.DismissedToEnd || it != DismissValue.DismissedToStart
                }
            )
            SwipeToDismiss(state = dismissState, background = {}) {
                NoteBox(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    note = note,
                    onClick = {
                        val data = Uri.encode(Gson().toJson(note))
                        navController.navigate(Screens.Edit.route + "?note=$data")
                    }
                )
            }
        }
    }
}