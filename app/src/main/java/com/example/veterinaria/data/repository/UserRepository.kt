package com.example.veterinaria.data.repository

import com.example.veterinaria.data.model.User
import com.google.firebase.firestore.CollectionReference
import com.example.veterinaria.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class UserRepository
@Inject
constructor(
    @Named("userCollection") private val userList: CollectionReference,
) {
    fun addNewUser(user: User) {
        try {
            // Agregar nuevo registro al usuario
            userList.document(user.userId).set(user)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    fun getUserList(): Flow<Result<List<User>>> = flow {
        try {
            emit(Result.Loading<List<User>>())
            val userList = userList.get().await().map { document ->
                document.toObject(User::class.java)
            }
            emit(Result.Success<List<User>>(data = userList))
        } catch (e: Exception) {
            emit(Result.Error<List<User>>(message = e.localizedMessage ?: "Error Desconocido"))
        }
    }
}
