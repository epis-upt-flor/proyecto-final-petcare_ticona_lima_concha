package com.example.veterinaria.ui.navegation

import androidx.navigation.NamedNavArgument
import androidx.navigation.navArgument

sealed class DestinationDiary(
    val route: String,
    val arguments: List<NamedNavArgument>
) {
    object DiaryList : DestinationDiary("diaryList", emptyList())
    object DiaryDetail : DestinationDiary(
        route ="diaryDetail",
        arguments = listOf(
            navArgument("diaryId"){
                nullable = true
            }
        )
    )
}