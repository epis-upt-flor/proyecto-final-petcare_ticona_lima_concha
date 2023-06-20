package com.example.veterinaria.data.model

data class Veterinary(
    val id: String = "",
    val name: String = "",
    val phone: String = "",
    val location: Location = Location(),
    val services: List<Service> = emptyList(),
    val veterinary_logo: String = ""
)