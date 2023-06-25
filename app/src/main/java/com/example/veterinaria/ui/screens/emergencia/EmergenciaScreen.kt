package com.example.veterinaria.ui.screens.emergencia

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.veterinaria.ui.navegation.PetRoutes
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

        LazyColumn(){
            items(
                items = state.emergency
            ){accident ->

                Button(
                    modifier = Modifier.fillMaxWidth()
                    ,onClick = {
                        navController.navigate(PetRoutes.EmergenciaMapRoute.name + "/${cont}")
                    }) {
                    Text(
                        text = "${accident.type} -> " +"$cont" ,
                    )
                }
                cont+=1
            }
        }

        Button(
            modifier = Modifier.fillMaxWidth()
            ,onClick = {
                navController.navigate(PetRoutes.EmergenciaMapRoute.name + "/0")
            }) {
            Text(
                text = "Atropello por auto",
            )
        }
        /*Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                navController.navigate(PetRoutes.EmergenciaMapRoute.name + "/1")
            }) {
            Text(
                text = "Problemas de parto",
            )
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                navController.navigate(PetRoutes.EmergenciaMapRoute.name + "/2")
            }) {
            Text(
                text = "Intoxicación",
            )
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                navController.navigate(PetRoutes.EmergenciaMapRoute.name + "/3")
            }) {
            Text(
                text = "Convulsiones",
            )
        }*/
    }
}
