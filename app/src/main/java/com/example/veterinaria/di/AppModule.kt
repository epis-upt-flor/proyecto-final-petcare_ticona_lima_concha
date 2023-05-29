package com.example.veterinaria.di

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
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



}