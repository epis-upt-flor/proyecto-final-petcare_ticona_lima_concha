package com.example.veterinaria.ui.components.splash

import androidx.annotation.DrawableRes
import com.example.veterinaria.R

sealed class OnBoardingPage(
    @DrawableRes
    val image: Int,
    val title: String,
    val description: String
) {
    object First : OnBoardingPage(
        image = R.drawable.first,
        title = "Encuentra Veterinarias cercanas con 1 toque.",
        description = "Encuentra veterinarias cercanas de manera rápida y sencilla. "
    )

    object Second : OnBoardingPage(
        image = R.drawable.second,
        title = "Respuesta inmediata para emergencias veterinarias",
        description = "El Botón de Emergencia de PetCare es tu salvavidas en situaciones de emergencia veterinaria. "
    )

    object Third : OnBoardingPage(
        image = R.drawable.third,
        title = "¡Bienvenido a PetCare! Tu compañero para el cuidado de tus mascotas",
        description = "nos emociona darte la bienvenida a nuestra aplicación."
    )
}