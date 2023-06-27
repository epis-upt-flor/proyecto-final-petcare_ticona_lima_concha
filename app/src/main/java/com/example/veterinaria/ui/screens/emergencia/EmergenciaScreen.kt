package com.example.veterinaria.ui.screens.emergencia

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.veterinaria.data.model.Emergency
import com.example.veterinaria.ui.navegation.PetRoutes
import com.example.veterinaria.ui.screens.home.standardQuadFromTo
import com.example.veterinaria.ui.theme.BlueViolet1
import com.example.veterinaria.ui.theme.ButtonBlue
import com.example.veterinaria.ui.theme.TextWhite
import com.example.veterinaria.viewmodel.EmergencyListViewModel

@Composable
fun EmergenciaScreen(
    navController: NavHostController
) {

    val viewModel3: EmergencyListViewModel = hiltViewModel()
    val state = viewModel3.state.value
    var cont = 0

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        SeccionEmergencias(state.emergency, navController)
    }
}

@Composable
fun SeccionEmergencias(emergencias: List<Emergency>, navController: NavHostController) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 5.dp, end = 5.dp)) {
        Text(
            text = "Emergencias",
            style = MaterialTheme.typography.h1,
            modifier = Modifier.padding(15.dp)
        )
        Text(text="Escoja el tipo de emergencia que tiene para mostrarle las clínicas cercanas que pueden ayudarle", modifier = Modifier.padding(start = 15.dp, end = 15.dp))
        LazyVerticalGrid(
            GridCells.Fixed(2),
            contentPadding = PaddingValues(start = 7.5.dp, end = 7.5.dp, bottom = 40.dp),
            modifier = Modifier.fillMaxHeight()
        ) {
            items(emergencias.size) {
                EmergenciaItemCard(emergencia = emergencias[it], navController)
            }
        }
    }
}


@Composable
fun EmergenciaItemCard(
    emergencia: Emergency,
    navController: NavHostController
) {
    Column() {
        Row(modifier = Modifier.fillMaxSize().padding(start = 6.dp, end = 6.dp, top = 6.dp).border(
            BorderStroke(2.dp, Color.Blue)
        ), horizontalArrangement = Arrangement.Center){
            Text(
                text = emergencia.type,
                style = MaterialTheme.typography.caption,
                lineHeight = 12.sp,
                modifier = Modifier.padding(2.dp)
            )
        }
        BoxWithConstraints(
            modifier = Modifier
                .padding(start = 6.dp, end = 6.dp, bottom = 6.dp)
                .aspectRatio(1f)
                .background(BlueViolet1)
                .border(BorderStroke(2.dp, Color.Blue))
        ) {

            val width = constraints.maxWidth
            val height = constraints.maxHeight

            // Light colored path
            val lightPoint1 = Offset(0f, height * 0.35f)
            val lightPoint2 = Offset(width * 0.1f, height * 0.4f)
            val lightPoint3 = Offset(width * 0.3f, height * 0.35f)
            val lightPoint4 = Offset(width * 0.65f, height.toFloat())
            val lightPoint5 = Offset(width * 1.4f, -height.toFloat() / 3f)

            val lightColoredPath = Path().apply {
                moveTo(lightPoint1.x, lightPoint1.y)
                standardQuadFromTo(lightPoint1, lightPoint2)
                standardQuadFromTo(lightPoint2, lightPoint3)
                standardQuadFromTo(lightPoint3, lightPoint4)
                standardQuadFromTo(lightPoint4, lightPoint5)
                lineTo(width.toFloat() + 100f, height.toFloat() + 100f)
                lineTo(-100f, height.toFloat() + 100f)
                close()
            }
            EmergenciaImagen(emergencia)

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp)
            ) {

                Text(
                    text = "Buscar",
                    color = TextWhite,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .clickable {
                            // Handle the click
                            navController.navigate(PetRoutes.EmergenciaMapRoute.name + "/" + emergencia.codigoEmergencia)
                        }
                        .align(Alignment.BottomEnd)
                        .clip(RoundedCornerShape(10.dp))
                        .background(ButtonBlue)
                        .padding(vertical = 6.dp, horizontal = 15.dp)
                )
            }
        }
    }
}

@Composable
private fun EmergenciaImagen(emergencia: Emergency){
    AsyncImage(model = emergencia.imagenUrl,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(corner = CornerSize(8.dp)))

    )
}
