package com.example.veterinaria.screens.diary.diary_detail

import com.example.veterinaria.modelos.Diary


data class DiaryDetailState(
    //estados
    val isLoading: Boolean = false,
    val diary: Diary? = null,
    val error: String = ""
)