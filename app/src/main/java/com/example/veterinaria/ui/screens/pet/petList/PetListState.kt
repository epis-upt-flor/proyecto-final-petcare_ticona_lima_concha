package com.example.veterinaria.ui.screens.pet.petList

import com.example.veterinaria.data.model.Pet

data class PetListState(
    val isLoading: Boolean = false,
    val pets: List<Pet> = emptyList(),
    val error: String = ""
)