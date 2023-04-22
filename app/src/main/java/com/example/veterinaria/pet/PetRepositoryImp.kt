package com.example.veterinaria.pet

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await

class PetRepositoryImp(
    private val database : FirebaseFirestore
) : PetRepositoryI {
    override suspend fun getListPet(): List<PetModel> {
        val result : QuerySnapshot = database.collection("pets").get().await()
        return if(result.isEmpty){
            emptyList()
        }else{
            result.documents.mapNotNull { documentSnapshot -> documentSnapshot.toObject(PetModel::class.java) }
        }
    }

    override suspend fun savePet(pet: PetModel): Boolean {
        return try {
            val document = database.collection("pets").document()
            pet.id = document.id
            document.set(pet).await()
            true
        }catch (error : Exception){
            false
        }
    }

}