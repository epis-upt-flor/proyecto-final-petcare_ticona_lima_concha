package com.example.veterinaria.di

import com.example.veterinaria.data.repository.ImageRepository
import com.example.veterinaria.data.repository.ImageRepositoryImpl
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
    fun provideFirebaseStorage()= Firebase.storage

    @Provides
    @Singleton
    @Named("vetCollection")
    fun provideVetCollection(
        firestore: FirebaseFirestore
    ): CollectionReference = firestore.collection("vet")

    @Provides
    @Singleton
    @Named("requestCollection")
    fun provideRequestCollection(
        firestore: FirebaseFirestore
    ): CollectionReference = firestore.collection("request")



    @Provides
    @Singleton
    @Named("userCollection")
    fun provideUserCollection(
        firestore: FirebaseFirestore
    ): CollectionReference = firestore.collection("users")

    @Provides
    @Singleton
    @Named("emergencyCollection")
    fun provideEmergencyCollection(
        firestore: FirebaseFirestore
    ): CollectionReference = firestore.collection("emergency")


    @Provides
    @Singleton
    @Named("petCollection")
    fun providePetCollection(
        firestore: FirebaseFirestore
    ): CollectionReference = firestore.collection("pet")

    @Provides
    @Singleton
    @Named("serviceCollection")
    fun provideServiceCollection(
        firestore: FirebaseFirestore
    ): CollectionReference = firestore.collection("services")

    @Provides
    @Singleton
    @Named("diaryList")
    fun provideDiaryList(
        firestore: FirebaseFirestore
    ): CollectionReference = firestore.collection("diarys")

    @Provides
    fun provideImaqeRepository(
        storage: FirebaseStorage,
        db: FirebaseFirestore
    ) : ImageRepository = ImageRepositoryImpl(
        storage = storage,
        db = db
    )
}