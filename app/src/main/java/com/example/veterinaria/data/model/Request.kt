package com.example.veterinaria.data.model

import com.google.firebase.firestore.FieldValue



data class Request(
    val id: String = "",
    val userid: String = "",
    val vetid: String = "",
    val date: FieldValue
)

data class RequestDetailState(
    //estados
    val isLoading: Boolean = false,
    val request: Request? = null,
    val error: String = ""
)