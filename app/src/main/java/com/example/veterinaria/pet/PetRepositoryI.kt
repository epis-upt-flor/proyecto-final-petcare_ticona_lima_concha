package com.example.veterinaria.pet

interface PetRepositoryI {
    suspend fun getListPet() : List<PetModel>
    suspend fun savePet(pet : PetModel) : Boolean
}