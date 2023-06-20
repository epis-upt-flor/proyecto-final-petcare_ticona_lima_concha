package com.example.veterinaria.ui.screens.user

import com.example.veterinaria.data.model.User

data class UserDetailState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val error: String = ""
)