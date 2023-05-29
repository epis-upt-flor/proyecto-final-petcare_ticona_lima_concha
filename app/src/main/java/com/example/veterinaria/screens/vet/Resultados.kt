package com.example.veterinaria.screens.vet

sealed class Resultados<T>(
    val data: T? = null,
    val message: String? = null
){
    // resultado sea correcto
    class Success<T>(data: T?) : Resultados<T>(data)

    class Error<T>(message: String?, data: T? = null) : Resultados<T>(data, message)

    class Loading<T>(data: T? = null) : Resultados<T>(data)
}