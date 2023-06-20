package com.example.veterinaria.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.veterinaria.data.model.Pet
import com.example.veterinaria.data.repository.PetRepository
import com.example.veterinaria.ui.screens.pet.petDetail.PetDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class PetDetailViewModel
@Inject
constructor(
    private val petRepository: PetRepository,
    savedStateHandle: SavedStateHandle // almacenamiento del id
) : ViewModel() {

    private val _state: MutableState<PetDetailState> = mutableStateOf(PetDetailState())
    val state: State<PetDetailState>
        get() = _state

    //fun addNewPet(name: String, age: Int, species: List<String>, breed:List<String>,gender:String,ownerId: String) {
    fun addNewPet(name: String, age: Int,species: List<String>,ownerId: String): String {
        val pet = Pet(
            id = UUID.randomUUID().toString(),
            name = name,
            age = age,
            species = species,
            breed = listOf(""),
            gender = "macho",
            ownerId = ownerId
        )
        petRepository.addNewPet(pet)

        return pet.id
    }


}
