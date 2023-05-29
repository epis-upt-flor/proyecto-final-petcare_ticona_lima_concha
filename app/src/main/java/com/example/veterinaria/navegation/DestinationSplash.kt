package com.example.veterinaria.navegation

sealed class DestinationSplash(
    val route: String
) {
    object Home : DestinationSplash(
        route = "home"
    )
    object Profile : DestinationSplash(
        route = "profile"
    )
}
