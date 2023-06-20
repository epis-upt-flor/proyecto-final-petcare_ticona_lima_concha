package com.example.veterinaria.ui.navegation

import androidx.navigation.NamedNavArgument
import androidx.navigation.navArgument

sealed class DestinationPet(
    val route: String,
    val arguments: List<NamedNavArgument>
) {
    object PetList : DestinationDiary("petList", emptyList())
    object PetDetail : DestinationDiary(
        route = "petDetail",
        arguments =listOf(
            navArgument("diaryId"){
                nullable = true
            }
        )
    )
}