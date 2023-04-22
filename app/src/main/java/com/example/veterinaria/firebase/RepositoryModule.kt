package com.example.veterinaria.firebase

import com.example.veterinaria.pet.PetRepositoryI
import com.example.veterinaria.pet.PetRepositoryImp
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun providePetRepository(
        database : FirebaseFirestore
    ) : PetRepositoryI {
        return PetRepositoryImp(database)
    }
}