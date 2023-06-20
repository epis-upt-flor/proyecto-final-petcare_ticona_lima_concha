package com.example.veterinaria.data.repository

import com.example.veterinaria.data.model.Service
import com.example.veterinaria.util.Result
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class ServiceRepository
@Inject
constructor(
    @Named("serviceCollection") private val serviceList: CollectionReference
){
    fun getServiceList(): Flow<Result<List<Service>>> = flow {
        try {
            emit(Result.Loading<List<Service>>())
            val serviceList = serviceList.get().await().map { document ->
                document.toObject(Service::class.java)
            }
            emit(Result.Success<List<Service>>(data = serviceList))
        } catch (e: Exception) {
            emit(Result.Error<List<Service>>(message = e.localizedMessage ?: "Error Desconocido"))
        }
    }
}