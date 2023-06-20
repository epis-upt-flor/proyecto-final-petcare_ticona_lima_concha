package com.example.veterinaria.ui.screens.vet.vetList

import com.example.veterinaria.data.model.Veterinary

data class VetListState(
    val isLoading: Boolean = false,
    val vet: List<Veterinary> = emptyList(),
    val error: String = ""
)