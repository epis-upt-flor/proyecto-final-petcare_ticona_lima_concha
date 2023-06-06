package com.example.veterinaria.di

import com.example.veterinaria.screens.img.data.ImageRepositoryImpl
import com.example.veterinaria.screens.img.repository.ImageRepository
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideFirestoreInstance() = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    @Named("vetCollection")
    fun provideVetCollection(
        firestore: FirebaseFirestore
    ): CollectionReference = firestore.collection("vet")

    @Provides
    @Singleton
    @Named("diaryList")
    fun provideDiaryList(
        firestore: FirebaseFirestore
    ): CollectionReference = firestore.collection("diarys")

    @Provides
    fun provideFirebaseStorage()= Firebase.storage

    // @Provides
    //fun provideFirebaseFirestore() = Firebase.firestore

    @Provides
    fun provideImaqeRepository(
        storage: FirebaseStorage,
        db: FirebaseFirestore
    ) : ImageRepository = ImageRepositoryImpl(
        storage = storage,
        db = db
    )



}