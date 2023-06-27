package com.example.veterinaria.data.repository

import com.example.veterinaria.data.model.Request
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
class RequestRepository
@Inject
constructor(
    @Named("requestCollection") private val reqList: CollectionReference
) {
    fun addRequest(req: Request) {
        try {
            reqList.document(req.id).set(req)
        } catch (e: Exception){
            e.printStackTrace()
        }
    }

    fun getReqList() : Flow<Result<List<Request>>> = flow {
        try {
            emit(Result.Loading<List<Request>>())

            val reqList = reqList.get().await().map{ document ->
                document.toObject(Request::class.java)
            }
            emit(Result.Success<List<Request>>(data = reqList))
        } catch (e: Exception) {
            emit(Result.Error<List<Request>>(message = e.localizedMessage ?: "Error Desconocido"))
        }
    }


}