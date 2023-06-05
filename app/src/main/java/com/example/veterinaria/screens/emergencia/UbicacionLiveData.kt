package com.example.veterinaria.screens.emergencia

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LiveData
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices

class UbicacionLiveData(var context: Context) : LiveData<DetallesUbicacion>() {

    private var fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    override fun onActive() {
        super.onActive()
        if(ActivityCompat.checkSelfPermission(
                context,
                ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            location.also {
                setLocationData(location)
            }
        }
        startLocationUpdates()
    }

    override fun onInactive() {
        super.onInactive()
        // turn off location updates
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    @SuppressLint("MissingPermission")
    internal fun startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            super.onLocationResult(locationResult)

            if(locationResult == null) {
                return
            }

            for (location in locationResult.locations) {
                setLocationData(location)
            }
        }
    }

    private fun setLocationData(location: Location?) {
        location?.let { location ->
            this.value = DetallesUbicacion(location.longitude.toString(), location.latitude.toString())
        }
    }

    companion object {
        val ONE_MINUTE : Long = 60000
        val locationRequest : LocationRequest = LocationRequest.create().apply {
            interval = ONE_MINUTE/2
            fastestInterval = ONE_MINUTE/4
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }
}