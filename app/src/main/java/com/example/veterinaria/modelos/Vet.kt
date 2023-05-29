package com.example.veterinaria.modelos

data class Vet(
    val id: String,
    val name: String,
    val phone: String,
    val location: String,
    val services: String,
    val veterinary_logo: String
){
    constructor() : this("","","","","","")
}