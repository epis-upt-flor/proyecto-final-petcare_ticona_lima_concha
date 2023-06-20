package com.example.veterinaria.ui.components

import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.veterinaria.ui.navegation.OpcionMenuInferior

@Composable
fun BarraNavegacionInferior(navController: NavHostController, navItemsList: List<OpcionMenuInferior>) {
    BottomAppBar() {
        BottomNavigation() {
            val rutaActual = ObtenerRutaActual(navController)
            navItemsList.forEach { item ->
                BottomNavigationItem(
                    selected = rutaActual == item.ruta,
                    onClick = { navController.navigate(item.ruta) },
                    icon = { Icon(imageVector = item.icono, contentDescription = item.titulo) },
                    label = { Text(text = item.titulo) }
                )
            }
        }
    }
}

@Composable
fun ObtenerRutaActual(navController: NavHostController): String? {
    val input by navController.currentBackStackEntryAsState()
    return input?.destination?.route
}