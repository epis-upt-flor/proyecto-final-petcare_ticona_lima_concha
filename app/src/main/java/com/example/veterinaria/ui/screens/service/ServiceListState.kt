package com.example.veterinaria.ui.screens.service

import com.example.veterinaria.data.model.Service

data class ServiceListState(
    val isLoading: Boolean = false,
    val services: List<Service> = emptyList(),
    val error: String = ""
)