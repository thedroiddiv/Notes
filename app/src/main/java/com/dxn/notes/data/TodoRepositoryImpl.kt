package com.dxn.notes.data

import com.dxn.notes.common.models.Result
import com.dxn.notes.common.models.Todo
import com.dxn.notes.domain.TodoRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

class TodoRepositoryImpl(
    private val auth: FirebaseAuth,
    private val userCollection: CollectionReference,
) : TodoRepository {
    @ExperimentalCoroutinesApi
    override fun getALlTodos() = callbackFlow {
        val listener = userCollection.document(auth.uid!!).collection("todos").apply { }
            .addSnapshotListener { value, firestoreException ->
                trySend(Result.Loading())
                if (firestoreException != null) {
                    cancel(
                        message = "error fetching collection data at path - $",
                        cause = firestoreException
                    )
                    firestoreException.printStackTrace()
                    trySend(Result.Error(firestoreException.message))
                    return@addSnapshotListener
                }
                trySend(Result.Success((value?.toObjects(Todo::class.java))!!))
            }
        awaitClose { listener.remove() }
    }

    override suspend fun addTodo(todo: Todo): Result<Boolean> {
        return try {
            userCollection.document(auth.uid!!)
                .collection("todos").document(todo.uid).set(todo)
            Result.Success(true)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(e.message)
        }
    }

    override suspend fun markAsDone(uid: String): Result<Boolean> {
        return try {
            userCollection.document(auth.uid!!)
                .collection("todos")
                .document(uid).update("isDone", true)
            Result.Success(true)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(e.message)
        }
    }

    override suspend fun markAsNotDone(uid: String): Result<Boolean> {
        return try {
            userCollection.document(auth.uid!!)
                .collection("todos")
                .document(uid).update("isDone", false)
            Result.Success(true)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(e.message)
        }
    }


    override suspend fun deleteTodo(uid: String): Result<Boolean> {
        return try {
            userCollection.document(auth.uid!!)
                .collection("todos")
                .document(uid)
                .delete()
            Result.Success(true)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(e.message)
        }
    }
}