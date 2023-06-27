package com.example.veterinaria.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.veterinaria.data.model.Emergency
import com.example.veterinaria.data.model.Location
import com.example.veterinaria.data.model.Service
import com.example.veterinaria.data.model.Veterinary
import com.example.veterinaria.data.repository.VetRepository
import com.example.veterinaria.ui.screens.vet.vetDetail.VetDetailState
import com.example.veterinaria.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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

    fun addNewVet(name: String, phone:String,address:String, location: Location, services: List<Service>, emergency: List<Emergency>,logo:String):String {
        val vet = Veterinary(
            id = UUID.randomUUID().toString(),
            name = name,
            phone = phone,
            address = address,
            location = location,
            services = services,
            emergency = emergency,
            veterinary_logo = logo,
            state = "Inactivo"
        )
        vetRepository.addNewVet(vet)
        return vet.id
    }



    init {
        Log.d("DetailViewModel", "SavedStateHandle...")
        savedStateHandle.get<String>("veterinaryId")?.let { veterinaryId ->
            Log.d("VeterinaryDetailViewModel", "VeterinaryId: $veterinaryId")
            getVeterinary(veterinaryId)
        }
    }
    private fun getVeterinary(veterinaryId: String) {
        vetRepository.getVeterinaryById(veterinaryId).onEach { result ->
            when (result) {
                /*is Result.Error -> {
                    _state.value = VetDetailState(error = result.message ?: "Unexpected error")
                }
                is Result.Loading -> {
                    _state.value = VetDetailState(isLoading = true)
                }
                is Result.Success -> {
                    _state.value = VetDetailState(vet = result.data)
                }*/
                is Result.Error -> {
                    _state.value = VetDetailState(error = result.message ?: "Unexpected error")
                }
                is Result.Loading -> {
                    _state.value = VetDetailState(isLoading = true)
                }
                is Result.Success -> {
                    _state.value = VetDetailState(vet = result.data)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun updateVeterinary(
//        newName: String,
//        newPhone: String,
//        newAddress: String,
//        newLocation: Location,
//        newServices: List<Service>,
//        newEmergency: List<Emergency>,
//        newVeterinaryLogo: String,
        newState: String
    ) {
        if (state.value.vet == null) {
            _state.value = VetDetailState(error = "Veterinary is null")
            return
        }
        val veterinaryEdited = state.value.vet!!.copy(
//            name = newName,
//            phone = newPhone,
//            address = newAddress,
//            location = newLocation,
//            services = newServices,
//            emergency = newEmergency,
//            veterinary_logo = newVeterinaryLogo,
            state = newState
        )
        vetRepository.updateVeterinary(veterinaryEdited.id, veterinaryEdited)
    }


}