package com.example.veterinaria.data.model

data class Diary (
    val id: String,
    val pet: String,
    val date: String,
    val hour: String,
    val service: String,
    val description: String
){
    constructor() : this("","","","","","")
}