package com.example.veterinaria.data.model

data class Pet (
    var id: String = "",
    var name: String= "",
    var age: Int= 0,
    var species: String = "",
    var breed: String = "",
    var gender: String = "",
    var photo: String= "",
    var ownerId: String = "" // ID del propietario (usuario) de la mascota
)