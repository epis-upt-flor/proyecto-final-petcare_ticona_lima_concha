package com.example.veterinaria.ui.screens.diary.diary_list

import com.example.veterinaria.data.model.Diary

data class DiaryListState(
    val isLoading: Boolean = false,
    val diarys: List<Diary> = emptyList(),
    val error: String = ""
)

