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
}