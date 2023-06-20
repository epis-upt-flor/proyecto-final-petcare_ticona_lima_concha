package com.example.veterinaria.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.veterinaria.data.repository.ServiceRepository
import com.example.veterinaria.ui.screens.service.ServiceListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ServiceListViewModel
@Inject
constructor(
    private val serviceRepository: ServiceRepository
) : ViewModel() {

    private val _state: MutableState<ServiceListState> = mutableStateOf(ServiceListState())
    val state: State<ServiceListState> = _state

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    init {
        getServiceList()
    }

    fun getServiceList() {
        serviceRepository.getServiceList().onEach { result ->
            when (result) {
                is com.example.veterinaria.util.Result.Error -> {
                    _state.value = ServiceListState(error = result.message ?: "Error Inesperado")
                }
                is com.example.veterinaria.util.Result.Loading -> {
                    _state.value = ServiceListState(isLoading = true)
                }
                is com.example.veterinaria.util.Result.Success -> {
                    _state.value = ServiceListState(services = result.data ?: emptyList())
                }
            }
        }.launchIn(viewModelScope)
    }
}
