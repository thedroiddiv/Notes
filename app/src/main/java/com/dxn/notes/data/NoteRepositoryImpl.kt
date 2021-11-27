package com.dxn.notes.data

import com.dxn.notes.common.models.Note
import com.dxn.notes.common.models.Result
import com.dxn.notes.domain.NoteRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await


class NoteRepositoryImpl(
    private val auth: FirebaseAuth,
    private val userCollection: CollectionReference,
) : NoteRepository {
    @ExperimentalCoroutinesApi
    override fun getAllNotes(): Flow<Result<List<Note>>> = callbackFlow {
        val listener = userCollection.document(auth.uid!!).collection("notes")
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
                trySend(Result.Success((value?.toObjects(Note::class.java))!!))
            }
        awaitClose { listener.remove() }
    }

    override suspend fun addNote(note: Note): Result<Boolean> {
        return try {
            userCollection.document(auth.uid!!)
                .collection("notes").document(note.uid).set(note)
            Result.Success(true)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(e.message)
        }
    }

    override suspend fun getNote(uid: String): Result<Note> {
        return try {
            val note = userCollection.document(auth.uid!!)
                .collection("notes")
                .whereEqualTo("uid", uid)
                .get().await()
                .toObjects(Note::class.java)[0]
            Result.Success(note)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(e.message)
        }
    }

    override suspend fun removeNote(uid: String): Result<Boolean> {
        return try {
            userCollection.document(auth.uid!!)
                .collection("notes")
                .document(uid)
                .delete()
            Result.Success(true)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(e.message)
        }
    }
}