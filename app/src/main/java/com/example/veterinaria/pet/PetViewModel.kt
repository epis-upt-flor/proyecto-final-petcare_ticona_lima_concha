package com.example.veterinaria.pet

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PetViewModel @Inject constructor(
    private val repository : PetRepositoryI
) : ViewModel() {
    val listPets = MutableLiveData<List<PetModel>>()

    fun getPets(){
        viewModelScope.launch {
            listPets.value = repository.getListPet()
        }
    }
}