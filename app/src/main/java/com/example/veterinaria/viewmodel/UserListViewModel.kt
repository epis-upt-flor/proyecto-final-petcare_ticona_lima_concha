package com.example.veterinaria.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.veterinaria.data.repository.UserRepository
import com.example.veterinaria.ui.screens.user.UserListState
import com.example.veterinaria.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class UserListViewModel
@Inject
constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _state: MutableState<UserListState> = mutableStateOf(UserListState())
    val state: State<UserListState> = _state

    init {
        getUserList()
    }

    fun getUserList() {
        userRepository.getUserList().onEach { result ->
            when (result) {
                is Result.Error -> {
                    _state.value = UserListState(error = result.message ?: "Error Inesperado")
                }
                is Result.Loading -> {
                    _state.value = UserListState(isLoading = true)
                }
                is Result.Success -> {
                    _state.value = UserListState(userList =  result.data ?: emptyList())
                }
            }
        }.launchIn(viewModelScope)
    }
}