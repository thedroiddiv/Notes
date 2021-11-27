package com.dxn.notes.ui.screens.home

import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.dxn.notes.R
import com.dxn.notes.ui.components.*
import com.dxn.notes.ui.navigation.Screens
import com.dxn.notes.ui.screens.AppViewModel
import com.google.firebase.auth.FirebaseUser
import com.google.gson.Gson

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun HomeScreen(
    viewModel: AppViewModel,
    navController: NavHostController,
    user: FirebaseUser,
    signOut: () -> Unit
) {

    val notes by remember { viewModel.note }
    var isDialogVisible by remember { mutableStateOf(false) }


    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Screens.Edit.route) },
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.onPrimary
            ) {
                Icon(imageVector = Icons.Rounded.Add, contentDescription = "Add new note")
            }

        }
    ) {
        var searchQuery by remember { mutableStateOf("") }
        val focusManager = LocalFocusManager.current
        Column {
            Spacer(modifier = Modifier.height(32.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                TitleText(
                    modifier = Modifier.padding(start = 4.dp),
                    text = stringResource(id = R.string.app_name)
                )
                IconButton(onClick = { isDialogVisible = true }) {
                    Image(
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(32.dp),
                        painter = rememberImagePainter(data = user.photoUrl),
                        contentDescription = "Profile Picture"
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            SearchBar(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                onSearch = {

                },
                focusManager = focusManager
            )
            Spacer(modifier = Modifier.height(16.dp))
            if (notes.isEmpty()) {
                Column(
                    Modifier.fillMaxWidth(),
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
                items(notes) { note ->
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
            if (isDialogVisible) {
                AlertDialog(
                    onDismissRequest = { isDialogVisible = false },
                    confirmButton = {
                        TextButton(onClick = signOut) {
                            Text(text = "Yes")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { isDialogVisible = false }) {
                            Text(text = "No")
                        }
                    },
                    title = {
                        Text(text = "Sign out?")
                    },
                    backgroundColor = MaterialTheme.colors.background,
                    contentColor = MaterialTheme.colors.onBackground
                )
            }

        }
    }
}