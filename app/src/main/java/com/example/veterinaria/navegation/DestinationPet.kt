package com.example.veterinaria.navegation

import androidx.navigation.NamedNavArgument
import androidx.navigation.navArgument

sealed class estinationPet(
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