package com.example.veterinaria.ui.screens.pet.petDetail

import com.example.veterinaria.data.model.Pet

data class PetDetailState(
    val isLoading: Boolean = false,
    val pet: Pet? = null,
    val error: String = ""
)