package com.example.veterinaria.data.repository

import com.example.veterinaria.data.model.Pet
import com.example.veterinaria.util.Result
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class PetRepository
@Inject
constructor(
    @Named("petCollection") private val petList: CollectionReference
) {
    fun addNewPet(pet: Pet) {
        try {
            petList.document(pet.id).set(pet)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    fun getPetList(): Flow<Result<List<Pet>>> = flow {
        try {
            emit(Result.Loading<List<Pet>>())
            val petList = petList.get().await().map { document ->
                document.toObject(Pet::class.java)
            }
            emit(Result.Success<List<Pet>>(data = petList))
        } catch (e: Exception) {
            emit(Result.Error<List<Pet>>(message = e.localizedMessage ?: "Error Desconocido"))
        }
    }
}