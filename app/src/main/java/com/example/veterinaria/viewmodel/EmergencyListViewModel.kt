package com.example.veterinaria.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.veterinaria.data.model.Emergency
import com.example.veterinaria.data.model.EmergencyListState
import com.example.veterinaria.data.repository.EmergencyRepository
import com.example.veterinaria.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class EmergencyListViewModel
@Inject
constructor(
    private val emergencyRepository: EmergencyRepository
) : ViewModel() {

    private val _state: MutableState<EmergencyListState> = mutableStateOf(EmergencyListState())
    val state: State<EmergencyListState> = _state

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    init {
        getEmergencyList()
    }

    fun getEmergencyList() {
        emergencyRepository.getEmergencyList().onEach { result ->
            when(result) {
                is Result.Error -> {
                    _state.value = EmergencyListState(error = result.message ?: "Error Inesperado")
                }
                is Result.Loading -> {
                    _state.value = EmergencyListState(isLoading = true)
                }
                is Result.Success -> {
                    _state.value = EmergencyListState(emergency = result.data ?: emptyList())
                }
            }
        }.launchIn(viewModelScope)
    }
}
