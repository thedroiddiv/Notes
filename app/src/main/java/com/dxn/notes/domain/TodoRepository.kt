package com.dxn.notes.domain

import com.dxn.notes.common.models.Result
import com.dxn.notes.common.models.Todo
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    fun getALlTodos(): Flow<Result<List<Todo>>>
    suspend fun addTodo(todo: Todo): Result<Boolean>
    suspend fun markAsDone(uid: String): Result<Boolean>
    suspend fun markAsNotDone(uid: String): Result<Boolean>
    suspend fun deleteTodo(uid: String): Result<Boolean>
}