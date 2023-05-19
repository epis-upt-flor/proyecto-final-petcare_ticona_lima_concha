package com.example.veterinaria.screens.emergencia

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.veterinaria.navegation.PetRoutes

@Composable
fun EmergenciaScreen(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Button(
            modifier = Modifier.fillMaxWidth()
            ,onClick = {
                navController.navigate(PetRoutes.EmergenciaMapRoute.name)
            }) {
            Text(
                text = "Atropello por auto",
            )
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                navController.navigate(PetRoutes.EmergenciaMapRoute.name)
            }) {
            Text(
                text = "Problemas de parto",
            )
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                navController.navigate(PetRoutes.EmergenciaMapRoute.name)
            }) {
            Text(
                text = "Intoxicación",
            )
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                navController.navigate(PetRoutes.EmergenciaMapRoute.name)
            }) {
            Text(
                text = "Convulsiones",
            )
        }
    }
}
