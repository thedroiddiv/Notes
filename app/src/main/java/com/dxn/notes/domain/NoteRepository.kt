package com.dxn.notes.domain

import com.dxn.notes.common.models.Note
import com.dxn.notes.common.models.Result
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getAllNotes(): Flow<Result<List<Note>>>
    suspend fun addNote(note: Note): Result<Boolean>
    suspend fun getNote(uid: String): Result<Note>
    suspend fun removeNote(uid: String): Result<Boolean>
}