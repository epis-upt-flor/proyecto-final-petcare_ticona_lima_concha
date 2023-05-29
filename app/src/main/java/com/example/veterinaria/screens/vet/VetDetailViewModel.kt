package com.example.veterinaria.screens.vet

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.veterinaria.modelos.Vet
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

    fun addNewVet(name: String,phone:String, location: String,services: String) {
        val vet = Vet(
            id = UUID.randomUUID().toString(),
            name = name,
            phone = phone,
            location = location,
            services = services,
            veterinary_logo = "https://previews.123rf.com/images/artsonik/artsonik1609/artsonik160900050/63391001-logotipo-para-cl%C3%ADnica-veterinaria-o-tienda-de-animales-un-perro-de-la-pata-estilizada-o-gato-signo.jpg"
        )
        // enviando informacion a firebase
        vetRepository.addNewVet(vet)
    }
}