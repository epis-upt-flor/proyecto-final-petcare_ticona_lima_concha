package com.example.veterinaria.screens.vet

import com.example.veterinaria.modelos.Vet
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
    // agregando nuevo documento a nuestra coleccion
    fun addNewVet(vet: Vet) {
        try {
            //agregando nuevo registro
            vetList.document(vet.id).set(vet)
        } catch (e: Exception){
            e.printStackTrace()
        }
    }

    //emitir resultado
    fun getVetList() : Flow<Resultados<List<Vet>>> = flow {
        try {
            emit(Resultados.Loading<List<Vet>>())

            val vetList = vetList.get().await().map{ document ->
                document.toObject(Vet::class.java)
            }

            emit(Resultados.Success<List<Vet>>(data = vetList))

        } catch (e: Exception) {
            emit(Resultados.Error<List<Vet>>(message = e.localizedMessage ?: "Error Desconocido"))
        }
    }
}