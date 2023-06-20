package com.example.veterinaria.ui.navegation

import androidx.navigation.NamedNavArgument
import androidx.navigation.navArgument

sealed class DestinationVet(
    val route: String,
    val arguments: List<NamedNavArgument>
) {
    object VetList : DestinationVet("vetList", emptyList())
    object VetDetail : DestinationVet(
        route ="vetDetail",
        arguments = listOf(
            navArgument("vetId"){
                nullable = true
            }
        )
    )
}