package com.example.veterinaria.screens.diary.diary_list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.veterinaria.screens.diary.diary_list.repositories.DiaryRepository
import com.example.veterinaria.screens.diary.diary_list.repositories.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DiaryListViewModel
@Inject
constructor(
    private val diaryRepository: DiaryRepository
) : ViewModel() {

    private val _state: MutableState<DiaryListState> = mutableStateOf(DiaryListState())
    val state: State<DiaryListState> = _state

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    init {
        getDiaryList()
    }

    fun getDiaryList() {
        diaryRepository.getDiaryList().onEach { result ->
            when(result) {
                is Result.Error -> {
                    _state.value = DiaryListState(error = result.message ?: "Error Inesperado")
                }
                is Result.Loading -> {
                    _state.value = DiaryListState(isLoading = true)
                }
                is Result.Success -> {
                    _state.value = DiaryListState(diarys = result.data ?: emptyList())
                }
            }
        }.launchIn(viewModelScope)
    }
}