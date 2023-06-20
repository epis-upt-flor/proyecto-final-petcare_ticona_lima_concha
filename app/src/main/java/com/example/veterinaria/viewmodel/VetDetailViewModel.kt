package com.example.veterinaria.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.veterinaria.data.model.Location
import com.example.veterinaria.data.model.Service
import com.example.veterinaria.data.model.Veterinary
import com.example.veterinaria.data.repository.VetRepository
import com.example.veterinaria.ui.screens.vet.vetDetail.VetDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class VetDetailViewModel
@Inject
constructor(
    private val vetRepository: VetRepository,
    savedStateHandle: SavedStateHandle // almacenamiento del id
): ViewModel() {

    private val _state: MutableState<VetDetailState> = mutableStateOf(VetDetailState())
    val state: State<VetDetailState>
        get() = _state

    fun addNewVet(name: String, phone:String, location: Location, services: List<Service>, logo:String) {
        val vet = Veterinary(
            id = UUID.randomUUID().toString(),
            name = name,
            phone = phone,
            location = location,
            services = services,
            veterinary_logo = logo
        )
        vetRepository.addNewVet(vet)
    }


}