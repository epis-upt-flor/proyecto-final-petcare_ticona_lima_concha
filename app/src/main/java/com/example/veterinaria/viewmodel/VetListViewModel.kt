package com.example.veterinaria.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.veterinaria.data.repository.VetRepository
import com.example.veterinaria.ui.screens.vet.vetList.VetListState
import com.example.veterinaria.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class VetListViewModel
@Inject
constructor(
    private val vetRepository: VetRepository,
) : ViewModel() {

    private val _state: MutableState<VetListState> = mutableStateOf(VetListState())
    val state: State<VetListState> = _state

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    init {
        getListVet()
    }
    fun getListVet() {
        vetRepository.getVetList().onEach { result ->
            when (result) {
                is Result.Error -> {
                    _state.value = VetListState(error = result.message ?: "Error Inesperado")
                }
                is Result.Loading -> {
                    _state.value = VetListState(isLoading = true)
                }
                is Result.Success -> {
                    _state.value = VetListState(vet = result.data ?: emptyList())
                }
            }
        }.launchIn(viewModelScope)
    }
}