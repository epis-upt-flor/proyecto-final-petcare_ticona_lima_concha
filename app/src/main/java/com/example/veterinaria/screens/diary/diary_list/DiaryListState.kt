package com.example.veterinaria.screens.diary.diary_list

import com.example.veterinaria.modelos.Diary

data class DiaryListState(
    val isLoading: Boolean = false,
    val diarys: List<Diary> = emptyList(),
    val error: String = ""
)

