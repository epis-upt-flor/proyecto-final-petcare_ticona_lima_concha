package com.example.veterinaria.pet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PetRegisterViewModel @Inject constructor(
    private val repository : PetRepositoryI
) : ViewModel(){
    val genero = MutableLiveData<String>()
    val edad = MutableLiveData<String>()
    val raza = MutableLiveData<String>()
    val foto = MutableLiveData<String>()

    val isSubmitting = MutableLiveData<Boolean>()

//    init {
//        genero.value = ""
//    }

    fun onPetRegisterChanged(
        genero :String ,
        edad :String ,
        raza :String ,
        foto :String ,
    ){
        this.genero.value = genero
        this.edad.value = edad
        this.raza.value = raza
        this.foto.value = foto
    }

    suspend fun submitPetRegister(){
//        viewModelScope.launch {
            isSubmitting.value = true
            repository.savePet(PetModel( null, raza.value!! , genero.value!!, edad.value!! , foto.value!!))
//        }

    }
}