package com.example.veterinaria.util

import java.lang.Exception

sealed class Result<T>(
    val data: T? = null,
    val message: String? = null
){
    class Success<T>(data: T?) : Result<T>(data)

    class Error<T>(message: String?, data: T? = null) : Result<T>(data, message)

    class Loading<T>(data: T? = null) : Result<T>(data)
}

sealed class Response <out T>{

    object Loading: Response<Nothing>()

    data class Sucess<out T>( val data: T?):Response<T>()

    data class Failure( val e: Exception ): Response<Nothing>()
}