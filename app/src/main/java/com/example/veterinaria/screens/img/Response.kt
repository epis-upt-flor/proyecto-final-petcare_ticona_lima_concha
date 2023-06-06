package com.example.veterinaria.screens.img

import java.lang.Exception

sealed class Response <out T>{
    object Loading: Response<Nothing>()

    data class Sucess<out T>(
        val data: T?
    ):Response<T>()
    data class Failure(
        val e: Exception
    ): Response<Nothing>()


}