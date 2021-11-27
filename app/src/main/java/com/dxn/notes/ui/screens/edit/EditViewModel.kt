package com.dxn.notes.ui.screens.edit

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dxn.notes.common.models.Note
import com.dxn.notes.domain.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class EditViewModel
@Inject
constructor(
    private val noteRepository: NoteRepository
) : ViewModel() {

    var title = mutableStateOf("")
    val text = mutableStateOf("")
    val selectedColor = mutableStateOf(0)

    fun initValues(note: Note?) {
        title.value = note?.title ?: ""
        text.value = note?.text ?: ""
        selectedColor.value = note?.let { Note.colors.indexOf(it.color) } ?: 0
    }

    fun saveNote(uid: String?, onSave: () -> Unit) {
        viewModelScope.launch {
            val note = Note(
                title.value,
                text.value,
                Note.colors[selectedColor.value],
                System.currentTimeMillis(),
                uid ?: System.currentTimeMillis().toString()
            )
            noteRepository.addNote(note)
            onSave()
        }
    }
}