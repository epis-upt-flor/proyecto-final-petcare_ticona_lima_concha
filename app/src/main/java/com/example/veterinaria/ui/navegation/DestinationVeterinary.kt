package com.example.veterinaria.ui.navegation

import androidx.navigation.NamedNavArgument
import androidx.navigation.navArgument

sealed class DestinationVeterinary(
    val route: String,
    val arguments: List<NamedNavArgument>
) {
    object VeterinaryList : DestinationVeterinary("vetList", emptyList())
    object VeterinaryDetail : DestinationVeterinary(
        route = "vetDetail",
        arguments = listOf(
            navArgument("vetId") {
                nullable = true
            }
        )
    )
}