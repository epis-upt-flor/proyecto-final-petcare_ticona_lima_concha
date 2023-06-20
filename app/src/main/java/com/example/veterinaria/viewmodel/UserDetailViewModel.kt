package com.example.veterinaria.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.veterinaria.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel
@Inject
constructor(
    private val userRepository: UserRepository,
    savedStateHandle: SavedStateHandle // almacenamiento del id
): ViewModel() {
}