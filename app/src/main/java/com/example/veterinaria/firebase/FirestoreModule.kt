package com.example.veterinaria.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class FirestoreModule {
//    @Provides
//    @Singleton
//    fun provideFirestoreInstance() : FirebaseFirestore {
//        return FirebaseFirestore.getInstance()
//    }

    @Singleton
    @Provides
    fun provideFirebaseStroageInstance(): StorageReference {
        return FirebaseStorage.getInstance().reference
    }
}