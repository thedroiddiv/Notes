package com.dxn.notes.di

import com.dxn.notes.data.NoteRepositoryImpl
import com.dxn.notes.domain.NoteRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NoteModule {

    @Provides
    @Singleton
    fun provideFirestoreDb() = Firebase.firestore

    @Singleton
    @Provides
    fun provideNoteRepository(
        auth: FirebaseAuth,
        firebaseFirestore: FirebaseFirestore
    ): NoteRepository = NoteRepositoryImpl(
        auth,
        firebaseFirestore.collection("users")
    )
}