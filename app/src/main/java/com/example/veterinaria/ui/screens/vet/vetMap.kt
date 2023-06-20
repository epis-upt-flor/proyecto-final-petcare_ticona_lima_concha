package com.example.veterinaria.ui.screens.vet

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
        position = CameraPosition.fromLatLngZoom(tacna, 16f)
    }

    val selectedVet = remember { mutableStateOf<Veterinary?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 1.dp, top = 1.dp, end = 1.dp, bottom = 200.dp),
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
                        selectedVet.value = state.vet.find { it.phone == vet.phone }
                        selectedVet.value = state.vet.find { it.veterinary_logo == vet.veterinary_logo }
                        selectedVet.value = state.vet.find { it.services == vet.services }
                        true
                    }
                )
            }
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(start = 1.dp, top = 1.dp, end = 1.dp, bottom = 60.dp)
                .background(Color.Green)
                .border(2.dp, Color.Black)
        ) {
            Text(text = "Informacion")
            if (selectedVet.value != null) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Veterinario seleccionado: ${selectedVet.value!!.name}")
                    Text(text = "Veterinario seleccionado: ${selectedVet.value!!.phone}")
                    val imageUrl = selectedVet.value!!.veterinary_logo
                    Image(
                        painter = rememberImagePainter(imageUrl),
                        contentDescription = "Veterinario seleccionado",
                        modifier = Modifier.size(70.dp).align(Alignment.End)
                    )

                    val servicesText = selectedVet.value!!.services.joinToString { it.name }
                    Text(text = "Veterinario seleccionado: $servicesText")
                }
            }
        }
    }
    }
