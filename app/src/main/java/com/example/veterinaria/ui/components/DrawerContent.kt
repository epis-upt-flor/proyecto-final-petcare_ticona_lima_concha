package com.example.veterinaria.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.veterinaria.R
import com.example.veterinaria.ui.navegation.OpcionMenuSuperior
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun DrawerContent(scope: CoroutineScope, scaffoldState: ScaffoldState, navController: NavHostController){
    Column(
        Modifier
            .fillMaxWidth(),
    ) {
        InformacionUsuarioCard("Pedro", "pedro@gmail.com", "https://xsgames.co/randomusers/assets/images/favicon.png")
        OpcionSuperior(scope, scaffoldState, navController, OpcionMenuSuperior.Home)
        OpcionSuperior(scope, scaffoldState, navController, OpcionMenuSuperior.MisNotificaciones)
        OpcionSuperior(scope, scaffoldState, navController, OpcionMenuSuperior.CitasScreen)
        OpcionSuperior(scope, scaffoldState, navController, OpcionMenuSuperior.MisMascotasScreen)
        OpcionSuperior(scope, scaffoldState, navController, OpcionMenuSuperior.Emergencia)
        OpcionSuperior(scope, scaffoldState, navController, OpcionMenuSuperior.Veterinaria)
        OpcionSuperior(scope, scaffoldState, navController, OpcionMenuSuperior.MapaVeterinariaScreen)
        OpcionSuperior(scope, scaffoldState, navController, OpcionMenuSuperior.CerrarSesion)
        ZonaRedesSociales()
    }
}

@Composable
fun ZonaRedesSociales() {
    Row(modifier = Modifier.padding(8.dp) ,verticalAlignment = Alignment.CenterVertically) {
        Image(
            modifier = Modifier.size(50.dp),
            painter = painterResource(id = R.mipmap.ic_facebook_foreground),
            contentDescription = "facebook icon"
        )
        Image(
            modifier = Modifier.size(40.dp),
            painter = painterResource(id = R.mipmap.ic_whatsapp_foreground),
            contentDescription = "whatsapp icon"
        )
    }
}


@Composable
fun InformacionUsuarioCard(name: String, email: String, photoUrl: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        AsyncImage(model = photoUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(8.dp)
                .size(64.dp)
                .clip(CircleShape)
                .border(2.dp, Color.Gray, CircleShape)
        )
        Column {
            Text(text = name)
            Text(text = email)
        }
    }
}


@Composable
fun OpcionSuperior(coroutineScope: CoroutineScope, scaffoldState: ScaffoldState, navController: NavHostController, opcionMenuSuperior: OpcionMenuSuperior) {
    val selected = opcionMenuSuperior.ruta == ObtenerRutaActual(navController)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .drawBehind {
                val strokeWidth = 1f
                val x = size.width - strokeWidth
                val y = size.height - strokeWidth

                //Linea divisoria inferior
                drawLine(
                    color = Color.Cyan,
                    start = Offset(0f, y),
                    end = Offset(x, y),
                    strokeWidth = strokeWidth
                )
            }
            .clickable {
                coroutineScope.launch {
                    scaffoldState.drawerState.close()
                }
                navController.navigate(opcionMenuSuperior.ruta)
            },
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
        ) {
            Icon(
                imageVector = opcionMenuSuperior.icono,
                contentDescription = "${opcionMenuSuperior.titulo} Icon"
            )
            Text(
                modifier = Modifier
                    .padding(start = 24.dp),
                text = opcionMenuSuperior.titulo,
            )
        }
    }
}
