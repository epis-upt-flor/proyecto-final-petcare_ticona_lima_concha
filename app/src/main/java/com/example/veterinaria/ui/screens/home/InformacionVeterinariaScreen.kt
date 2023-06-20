package com.example.veterinaria.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.veterinaria.data.model.Veterinaria
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.AsyncImage
import com.example.veterinaria.ui.screens.emergencia.DetallesUbicacion
import com.google.maps.android.ktx.utils.sphericalDistance
import kotlin.math.roundToInt

@Composable
fun InformacionVeterinariaScreen() {
    val veterinarias = listOf(Veterinaria.LocalPrincipal, Veterinaria.Sucursal1, Veterinaria.Sucursal2)
    Column {
        LocalesVeterinaria(veterinarias, null)
        MapaVeterinarias()
    }
}

@Composable
fun MapaVeterinarias() {
    val tacna = LatLng(-18.00, -70.24)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(tacna, 16f)
    }
    GoogleMap(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 1.dp, top = 1.dp, end = 1.dp, bottom = 60.dp),
        cameraPositionState = cameraPositionState,
    ) {
        val locales = listOf(Veterinaria.LocalPrincipal, Veterinaria.Sucursal1, Veterinaria.Sucursal2)
        locales.forEach {local ->
            Marker(
                state = MarkerState(position = local.ubicacion),
                title = local.titulo,
                snippet = local.telefono
            )
        }
    }
}

@Composable
fun LocalesVeterinaria(veterinarias: List<Veterinaria>, ubicacionActual: DetallesUbicacion?) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(
            items = veterinarias,
            itemContent = {
                VeterinariaListItem(veterinaria = it, ubicacionActual)
            })
    }
}


@Composable
fun VeterinariaListItem(veterinaria: Veterinaria, ubicacionActual: DetallesUbicacion?) {
    Card(
        modifier = Modifier
            .padding(
                horizontal = 8.dp, vertical = 8.dp
            )
            .fillMaxWidth(),
        elevation = 2.dp,
        shape = RoundedCornerShape(corner = CornerSize(16.dp))
    ) {
        Row(Modifier.clickable { }) {
            VeterinariaImage(veterinaria = veterinaria)
            Column(modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .align(Alignment.CenterVertically)
            ) {
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = veterinaria.titulo, style = typography.h6)
                    Text(text = veterinaria.telefono, style = typography.caption, color = Color.Green)
                }
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = veterinaria.direccion,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                    style = typography.caption
                )
                ubicacionActual?.let{
                    var miUbicacion = LatLng(it.latitude.toDouble(), it.longitude.toDouble())
                    var distanciaEsferica = miUbicacion.sphericalDistance(veterinaria.ubicacion)
                    var distanciaEsfericaRedondeada = (distanciaEsferica * 100.0).roundToInt() / 100.0
                    Text(text = "Distancia: $distanciaEsfericaRedondeada m")
                }
            }
        }
    }
}

@Composable
private fun VeterinariaImage(veterinaria: Veterinaria){
    AsyncImage(model = "https://www.shutterstock.com/image-vector/blue-round-veterinary-icon-pets-260nw-320868806.jpg",
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .padding(2.dp)
            .size(100.dp)
            .clip(RoundedCornerShape(corner = CornerSize(8.dp)))
    )
}
