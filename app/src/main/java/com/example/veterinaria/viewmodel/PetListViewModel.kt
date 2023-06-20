package com.example.veterinaria.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.veterinaria.data.repository.PetRepository
import com.example.veterinaria.ui.screens.pet.petList.PetListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class PetListViewModel
@Inject
constructor(
    private val petRepository: PetRepository
) : ViewModel() {

    private val _state: MutableState<PetListState> = mutableStateOf(PetListState())
    val state: State<PetListState> = _state

    init {
        getPetList()
    }

    fun getPetList() {
        petRepository.getPetList().onEach { result ->
            when (result) {
                is com.example.veterinaria.util.Result.Error -> {
                    _state.value = PetListState(error = result.message ?: "Error Inesperado")
                }
                is com.example.veterinaria.util.Result.Loading -> {
                    _state.value = PetListState(isLoading = true)
                }
                is com.example.veterinaria.util.Result.Success -> {
                    _state.value = PetListState(pets = result.data ?: emptyList())
                }
            }
        }.launchIn(viewModelScope)
    }
}