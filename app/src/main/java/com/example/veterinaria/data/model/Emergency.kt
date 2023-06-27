package com.example.veterinaria.data.model



data class Emergency(
    val id: String = "",
    val type: String = "",
    val description: String = "",
    val imagenUrl: String = "",
    val codigoEmergencia: String = ""
)

data class EmergencyListState(
    val isLoading: Boolean = false,
    val emergency: List<Emergency> = emptyList(),
    val error: String = ""
)