package com.example.veterinaria.modelos


data class  Pet(
    val id: String,
    val raza: String,
    val genero: String,
    val edad: String,
    val foto: String
) {
    constructor() : this("","","","","")
}