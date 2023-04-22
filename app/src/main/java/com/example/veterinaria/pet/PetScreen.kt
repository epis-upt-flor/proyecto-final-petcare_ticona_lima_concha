package com.example.veterinaria.pet

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter

@Composable
fun PetScreen(
    openRegisterPetScreen : () -> Unit,
    viewModel: PetViewModel = hiltViewModel()
) {
    val listPets by viewModel.listPets.observeAsState(initial = emptyList())
    
    LaunchedEffect(key1 = Unit){
        viewModel.getPets()
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)){
        Button(onClick = {  openRegisterPetScreen() }) {
            Text(text = "Nueva mascota")
        }
        LazyColumn(Modifier.weight(1f)){
            items(listPets){
                Card() {
                    Row() {
                        Image(painter = rememberAsyncImagePainter(it.foto), contentDescription = null, modifier = Modifier
                            .width(120.dp)
                            .height(120.dp) , contentScale = ContentScale.Crop)
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = "Genero : "+it.genero, style = MaterialTheme.typography.h6)
                            Text(text = "Edad : "+it.edad, style = MaterialTheme.typography.h6)
                            Text(text = "Raza : "+it.raza, style = MaterialTheme.typography.h6)
                        }
                    }
                }
            }
        }

    }
}