package com.example.veterinaria.ui.components.splash

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
