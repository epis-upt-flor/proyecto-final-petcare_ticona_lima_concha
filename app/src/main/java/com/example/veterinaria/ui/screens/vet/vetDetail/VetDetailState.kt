package com.example.veterinaria.ui.screens.vet.vetDetail

import com.example.veterinaria.data.model.Veterinary

data class VetDetailState(
    val isLoading: Boolean = false,
    val vet: Veterinary? = null,
    val error: String = ""
)