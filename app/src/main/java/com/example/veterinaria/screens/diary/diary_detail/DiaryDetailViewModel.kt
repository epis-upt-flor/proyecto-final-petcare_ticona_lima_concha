package com.example.veterinaria.screens.diary.diary_detail

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.veterinaria.modelos.Diary
import com.example.veterinaria.screens.diary.diary_list.repositories.DiaryRepository
import com.example.veterinaria.screens.diary.diary_list.repositories.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.*
import javax.inject.Inject

@HiltViewModel
class DiaryDetailViewModel
@Inject
constructor(
    private val diaryRepository: DiaryRepository,
    savedStateHandle: SavedStateHandle // almacenamiento del id
): ViewModel(){

    private val _state: MutableState<DiaryDetailState> = mutableStateOf(DiaryDetailState())
    val state: State<DiaryDetailState>
        get() = _state


    init {
        Log.d("DetailViewModel", "SavedStateHandle...")
        savedStateHandle.get<String>("diaryId")?.let { diaryId ->
            Log.d("DiaryDetailViewModel", "DiaryId: $diaryId")
            getDiary(diaryId)
        }
    }

    fun addNewDiary(pet: String, date: String, hour: String,service: String) {
        val diary = Diary(
            id = UUID.randomUUID().toString(),
            pet = pet,
            date = date,
            hour = hour,
            service = service,
            description = "mas"
        )
        // enviando informacion a firebase
        diaryRepository.addNewDiary(diary)
    }

    private fun getDiary(diaryId: String) {
        diaryRepository.getDiaryById(diaryId).onEach { result ->
            when(result){

                is Result.Error -> {
                    _state.value = DiaryDetailState(error = result.message ?: "Unexpected error")
                }
                is Result.Loading -> {
                    _state.value = DiaryDetailState(isLoading = true)
                }
                is Result.Success -> {
                    _state.value =   DiaryDetailState(diary = result.data)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun updateDiary(newPet: String,  newTime: String,newHour: String, newService: String) {
        if(state.value.diary == null){
            _state.value = DiaryDetailState(error = "Diary is null")
            return
        }

        val diaryEdited = state.value.diary!!.copy(
            pet = newPet,
            date = newTime,
            hour = newHour,
            service = newService
        )

        diaryRepository.updateDiary(diaryEdited.id, diaryEdited)
    }





}