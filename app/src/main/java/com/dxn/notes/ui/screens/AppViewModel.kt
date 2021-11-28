package com.dxn.notes.ui.screens

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dxn.notes.common.models.Note
import com.dxn.notes.common.models.Result
import com.dxn.notes.domain.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel
@Inject
constructor(
    private val noteRepository: NoteRepository
) : ViewModel() {

    private val filter = mutableStateOf("")
    private val _notes = mutableStateOf(listOf<Note>())
    val notes: State<List<Note>> = _notes
    private val error = mutableStateOf("")
    private val isLoading = mutableStateOf(false)

    var getAllJob: Job? = null

    init {
        loadNotes()
    }

    private fun loadNotes() {
        getAllJob?.cancel()
        getAllJob = noteRepository.getAllNotes().onEach {
            when (it) {
                is Result.Success -> {
                    _notes.value = it.data!!.filter { note: Note ->
                        note.text.contains(filter.value) || note.title.contains(filter.value)
                    }
                    isLoading.value = false
                }
                is Result.Loading -> {
                    isLoading.value = true
                }
                else -> {
                    error.value = it.message!!
                    isLoading.value = false
                }
            }
        }.launchIn(viewModelScope)
    }

    fun filterNotes(query: String) {
        filter.value = query
        notes
    }

    fun removeNote(uid: String) {
        viewModelScope.launch {
            noteRepository.removeNote(uid)
        }
    }
}