package com.example.veterinaria.data.model

import com.google.android.gms.maps.model.LatLng

class Location(
    val latitude: Double,
    val longitude: Double
) {
    // Constructor sin argumentos requerido por Firebase (si se usa)
    constructor() : this(0.0, 0.0)

    fun toLatLng(): LatLng {
        return LatLng(latitude, longitude)
    }
}