package com.example.veterinaria.data.repository

import com.example.veterinaria.data.model.Emergency
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton
import com.example.veterinaria.util.Result


@Singleton
class EmergencyRepository
@Inject
constructor(
    @Named("emergencyCollection") private val emergencyList: CollectionReference
){
    fun getEmergencyList(): Flow<Result<List<Emergency>>> = flow {
        try {
            emit(Result.Loading<List<Emergency>>())
            val emergencyList = emergencyList.get().await().map { document ->
                document.toObject(Emergency::class.java)
            }
            emit(Result.Success<List<Emergency>>(data = emergencyList))
        } catch (e: Exception) {
            emit(Result.Error<List<Emergency>>(message = e.localizedMessage ?: "Error Desconocido"))
        }
    }
}
