package com.example.veterinaria.ui.screens.user

import com.example.veterinaria.data.model.User

data class UserListState(
    val isLoading: Boolean = false,
    val userList: List<User> = emptyList(),
    val error: String = ""
)