package com.example.veterinaria.screens.vet

import com.example.veterinaria.modelos.Vet

data class VetDetailState(
    val isLoading: Boolean = false,
    val vet: Vet? = null,
    val error: String = ""
)