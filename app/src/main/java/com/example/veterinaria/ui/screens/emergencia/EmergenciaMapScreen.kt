package com.example.veterinaria.ui.screens.emergencia

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.veterinaria.data.model.Emergencias
import com.example.veterinaria.data.model.Veterinaria
import com.example.veterinaria.data.model.Veterinary
import com.example.veterinaria.ui.screens.home.LocalesVeterinaria
import com.example.veterinaria.ui.screens.vet.vetList.VetListState
import com.example.veterinaria.viewmodel.VetListViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@Composable
fun EmergenciaMapScreen(
    ubicacionLiveData: UbicacionLiveData,
    requestSinglePermissionLauncher: ActivityResultLauncher<String>,
    codigoEmergencia: String?
) {
    val viewModel: VetListViewModel = hiltViewModel()
    val state = viewModel.state.value
    val veterinariasDisponibles = LocalesQueAtiendenEmergencia(codigoEmergencia, state)
    Column(){
        val ubicacionActual by ubicacionLiveData.observeAsState()
        Card(modifier = Modifier.padding(8.dp)){
            Column(){
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
                    Text(text = "EMERGENCIA POR: " + codigoEmergencia, style = typography.caption, color = Color.Green)
                }
                LocalesVeterinaria(veterinariasDisponibles, ubicacionActual)
            }
        }
        MapaEmergenciaVeterinarias(ubicacionActual, veterinariasDisponibles)
        LocationUpdates(ubicacionLiveData, LocalContext.current, requestSinglePermissionLauncher)
    }
}

@Composable
fun MapaEmergenciaVeterinarias(location: DetallesUbicacion?, veterinariasDisponibles: List<Veterinary>) {
    val tacna = LatLng(-18.00, -70.24)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(tacna, 12f)
    }
    val uiSettings = remember {
        MapUiSettings(myLocationButtonEnabled = true)
    }
    val properties by remember {
        mutableStateOf(MapProperties(isMyLocationEnabled = true))
    }
    GoogleMap(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 1.dp, top = 1.dp, end = 1.dp, bottom = 60.dp),
        cameraPositionState = cameraPositionState,
        uiSettings = uiSettings,
        properties = properties
    ) {
        veterinariasDisponibles.forEach {local ->
            Marker(
                state = MarkerState(position = local.location.toLatLng()),
                title = local.name,
                snippet = local.phone
            )
        }

        location?.let {
            var miUbicacion = LatLng(location.latitude.toDouble(), location.longitude.toDouble())
            Marker(
                MarkerState(position = miUbicacion),
                title = "Estas aquí!"
            )
        }
    }
}

private fun LocationUpdates(
    ubicacionLiveData: UbicacionLiveData,
    context: Context,
    requestSinglePermissionLauncher: ActivityResultLauncher<String>
) {
    if(estaPermitidaLaUbicacion(context)) {
        ubicacionLiveData.startLocationUpdates()
    } else {
        requestSinglePermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }
}

private fun estaPermitidaLaUbicacion(context: Context): Boolean {
    return ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
}

private fun LocalesQueAtiendenEmergencia(codigoEmergencia: String?, state: VetListState): List<Veterinary> {

    val locales = state.vet.toList()
    var localesDisponibles = mutableListOf<Veterinary>()
    codigoEmergencia?.let {
        for (veterinaria: Veterinary in locales) {
            if(veterinaria.services.any{ it.codigoEmergencia == codigoEmergencia }) {
                localesDisponibles.add(veterinaria)
            }
        }
    }
    return localesDisponibles
}

