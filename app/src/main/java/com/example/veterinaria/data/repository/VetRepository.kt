package com.example.veterinaria.data.repository

import com.example.veterinaria.data.model.Veterinary
import com.example.veterinaria.util.Result
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class VetRepository
@Inject
constructor(
    @Named("vetCollection") private val vetList: CollectionReference
) {
    fun addNewVet(vet: Veterinary) {
        try {
            vetList.document(vet.id).set(vet)
        } catch (e: Exception){
            e.printStackTrace()
        }
    }
    fun getVetList() : Flow<Result<List<Veterinary>>> = flow {
        try {
            emit(Result.Loading<List<Veterinary>>())

            val vetList = vetList.get().await().map{ document ->
                document.toObject(Veterinary::class.java)
            }
            emit(Result.Success<List<Veterinary>>(data = vetList))
        } catch (e: Exception) {
            emit(Result.Error<List<Veterinary>>(message = e.localizedMessage ?: "Error Desconocido"))
        }
    }

    fun getVeterinaryById(veterinaryId: String): Flow<Result<Veterinary>> = flow {
        try {
            emit(Result.Loading<Veterinary>())

            val veterinary = vetList
                .whereEqualTo("id", veterinaryId)
                .get()
                .await()
                .toObjects(Veterinary::class.java)
                .firstOrNull()
            if (veterinary != null) {
                emit(Result.Success<Veterinary>(data = veterinary))
            } else {
                emit(Result.Error<Veterinary>(message = "Veterinary not found"))
            }
        } catch (e: Exception) {
            emit(Result.Error<Veterinary>(message = e.localizedMessage ?: "Unknown Error"))
        }
    }

    fun updateVeterinary(veterinaryId: String, veterinary: Veterinary) {
        try {
            val map = mapOf(
                "name" to veterinary.name,
                "phone" to veterinary.phone,
                "address" to veterinary.address,
                "location" to veterinary.location,
                "services" to veterinary.services,
                "emergency" to veterinary.emergency,
                "veterinary_logo" to veterinary.veterinary_logo,
                "state" to veterinary.state
            )
            vetList.document(veterinaryId).update(map)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}