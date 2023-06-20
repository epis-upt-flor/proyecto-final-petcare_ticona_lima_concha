package com.example.veterinaria.ui.screens.diary.diary_detail

import com.example.veterinaria.data.model.Diary


data class DiaryDetailState(
    //estados
    val isLoading: Boolean = false,
    val diary: Diary? = null,
    val error: String = ""
)