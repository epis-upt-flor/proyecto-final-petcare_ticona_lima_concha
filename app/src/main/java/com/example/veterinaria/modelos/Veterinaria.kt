package com.example.veterinaria.modelos

import com.google.android.gms.maps.model.LatLng

sealed class Veterinaria(
    val direccion: String,
    val titulo: String,
    val telefono: String,
    val ubicacion: LatLng,
    val emergenciasDisponibles: List<Emergencias>
) {
    object LocalPrincipal : Veterinaria(
        "Av. Augusto B Leguía #623 Frente al paseo de las Aguas",
        "Local Principal",
        "956895362",
        LatLng(-18.00282197470253, -70.23757545631742),
        listOf(Emergencias.ATROPELLO, Emergencias.PROBLEMASPARTO, Emergencias.INTOXICACION, Emergencias.CONVULSIONES)
    )

    object Sucursal1 : Veterinaria(
        "Av. Internacional #998",
        "Sucursal 1",
        "998263153",
        LatLng(-17.982525833287564, -70.23654022335187),
        listOf(Emergencias.ATROPELLO, Emergencias.INTOXICACION, Emergencias.CONVULSIONES)
    )

    object Sucursal2 : Veterinaria(
        "Av. La Cultura #26 Frente al mercado Santa Rosa",
        "Sucursal 2",
        "998563223",
        LatLng(-18.04555799517071, -70.25624424275371),
        listOf(Emergencias.ATROPELLO)
    )
}

enum class Emergencias {
    ATROPELLO, PROBLEMASPARTO, INTOXICACION, CONVULSIONES
}