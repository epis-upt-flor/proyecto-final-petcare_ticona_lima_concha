package com.example.veterinaria.screens.emergencia

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.veterinaria.modelos.Veterinaria
import com.example.veterinaria.screens.home.LocalesVeterinaria
import com.example.veterinaria.screens.home.MapaVeterinarias

@Composable
fun EmergenciaMapScreen() {
    val veterinarias = listOf(Veterinaria.LocalPrincipal)
    Column(){
        Card(modifier = Modifier.padding(8.dp)){
            Column(){
                LocalesVeterinaria(veterinarias)
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround) {
                    Text(text = "Distancia: 1200m")
                    Text(text = "Tiempo: 20 min")
                }
            }
        }
        MapaVeterinarias()
    }
}