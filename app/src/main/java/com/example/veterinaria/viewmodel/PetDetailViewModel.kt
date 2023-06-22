package com.example.veterinaria.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.veterinaria.data.model.Pet
import com.example.veterinaria.data.repository.PetRepository
import com.example.veterinaria.ui.screens.pet.petDetail.PetDetailState
import com.example.veterinaria.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class PetDetailViewModel
@Inject
constructor(
    private val petRepository: PetRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state: MutableState<PetDetailState> = mutableStateOf(PetDetailState())
    val state: State<PetDetailState>
        get() = _state

    init {
        Log.d("DetailViewModel", "SavedStateHandle...")
        savedStateHandle.get<String>("petId")?.let { petId ->
            Log.d("PetDetailViewModel", "DiaryId: $petId")
            getPet(petId)
        }
    }

    fun addNewPet(
        name: String,
        age: Int,
        species: String,
        breed:String,
        gender:String,
        photo:String,ownerId: String
    ): String {
        val pet = Pet(
            id = UUID.randomUUID().toString(),
            name = name,
            age = age,
            species = species,
            breed = breed,
            gender = gender,
            photo = photo,
            ownerId = ownerId
        )
        petRepository.addNewPet(pet)
        return pet.id
    }

    private fun getPet(petId: String) {
        petRepository.getPetById(petId).onEach { result ->
            when(result){
                is Result.Error -> {
                    _state.value = PetDetailState(error = result.message ?: "Unexpected error")
                }
                is Result.Loading -> {
                    _state.value = PetDetailState(isLoading = true)
                }
                is Result.Success -> {
                    _state.value =   PetDetailState(pet = result.data)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun updatePets(newName: String,
                    newAge: Int,
                    newSpecies: String,
                    newBreed: String,
                    newGender: String,
                    newPhoto: String
    ) {
        if(state.value.pet == null){
            _state.value = PetDetailState(error = "Pet is null")
            return
        }
        val petEdited = state.value.pet!!.copy(
            name = newName,
            age = newAge,
            species = newSpecies,
            breed = newBreed,
            gender = newGender,
            photo = newPhoto
        )
        petRepository.updatePet(petEdited.id, petEdited)
    }

}
