package com.example.veterinaria.ui.screens.vet

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.veterinaria.data.model.Veterinary
import com.example.veterinaria.ui.screens.vet.vetList.VetListState
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun vetMap(
    state: VetListState,
) {
    val selectedMarker = remember { mutableStateOf<MarkerState?>(null) }
    val tacna = LatLng(-18.00, -70.24)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(tacna, 12f)
    }

    val selectedVet = remember { mutableStateOf<Veterinary?>(null) }



    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(1.dp, 1.dp, 1.dp, 60.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Green)
                .weight(0.75f)
        ) {
            GoogleMap(
                modifier = Modifier
                    .fillMaxSize(),
                cameraPositionState = cameraPositionState
            ) {
                state.vet.forEach { vet ->
                    val location = vet.location
                    val markerState = remember {
                        mutableStateOf(
                            MarkerState(
                                position = LatLng(
                                    location.latitude,
                                    location.longitude
                                )
                            )
                        )
                    }

                    Marker(
                        state = markerState.value,
                        title = vet.name,
                        snippet = vet.phone,
                        onClick = {
                            selectedMarker.value = markerState.value
                            selectedVet.value = state.vet.find { it.name == vet.name }
                            true
                        }
                    )
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.25f)
                .height(IntrinsicSize.Min)
        ) {
            Row(modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center) {
                if (selectedVet.value != null) {
                    Card(modifier = Modifier.weight(1f)) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = "Nombre de Veterinaria: ${selectedVet.value!!.name}")
                            Text(text = "Numero: ${selectedVet.value!!.phone}")
                            val servicesText = selectedVet.value!!.services.joinToString { it.name }
                            Text(text = "Servicios: $servicesText")
                        }
                    }
                    Box(modifier = Modifier.weight(1f)) {
                        Image(
                            painter = rememberImagePainter(selectedVet.value?.veterinary_logo),
                            contentDescription = "Veterinario seleccionado",
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                } else {
                    Text(text = "Seleccione una veterinaria en el mapa para obtener información")
                }

            }
        }
    }
}