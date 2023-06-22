package com.example.veterinaria.data.repository

import com.example.veterinaria.data.model.Diary
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

    fun getPetById(petId: String): Flow<Result<Pet>> = flow {
        try {
            emit(Result.Loading<Pet>())
            val pet = petList
                .whereGreaterThanOrEqualTo("id", petId)
                .get()
                .await()
                .toObjects(Pet::class.java)
                .first()
            emit(Result.Success<Pet>(data = pet))
        } catch (e: Exception) {
            emit(Result.Error<Pet>(message = e.localizedMessage ?: "Error Desconocido"))
        }
    }

    fun updatePet(petId: String, pet: Pet) {
        try {
            val map = mapOf(
                "name"      to pet.name,
                "age"      to pet.age,
                "species"      to pet.species,
                "breed"   to pet.breed,
                "gender"   to pet.gender,
                "photo"   to pet.photo,
            )
            petList.document(petId).update(map)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }




}