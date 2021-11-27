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

val dummy =
    "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat"

@HiltViewModel
class AppViewModel
@Inject
constructor(
    private val noteRepository: NoteRepository
) : ViewModel() {

    private val _notes = mutableStateOf(listOf<Note>())
    val note: State<List<Note>> = _notes
    private val error = mutableStateOf("")
    private val isLoading = mutableStateOf(false)

    var getAllJob: Job? = null

    init {
        loadNotes()
    }

    private fun loadNotes() {
        getAllJob?.cancel()
       getAllJob =  noteRepository.getAllNotes().onEach {
            when (it) {
                is Result.Success -> {
                    _notes.value = it.data!!
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

    fun deleteNote() {

    }
}