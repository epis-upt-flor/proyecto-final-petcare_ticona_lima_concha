package com.example.veterinaria.screens.diary.diary_list.repositories

sealed class Result<T>(
    val data: T? = null,
    val message: String? = null
){
    // resultado sea correcto
    class Success<T>(data: T?) : Result<T>(data)

    class Error<T>(message: String?, data: T? = null) : Result<T>(data, message)

    class Loading<T>(data: T? = null) : Result<T>(data)
}