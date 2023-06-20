package com.example.veterinaria.ui.screens.pet.petList

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.google.firebase.auth.FirebaseAuth

@Composable
fun PetListScreen(
    state: PetListState,
) {
    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    val userId56 = auth.currentUser?.uid

    val filteredPetList = state.pets.filter { pet ->
        pet.ownerId == userId56
    }
    Box(
        modifier = Modifier.fillMaxSize().padding(bottom = 60.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            filteredPetList.forEach { pet ->
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    elevation = 4.dp,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        // Column for image
                        Column(
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            val imageUrl = pet.photo
                            Image(
                                painter = rememberImagePainter(imageUrl),
                                contentDescription = "Mascota seleccionada",
                                modifier = Modifier
                                    .size(100.dp)
                            )
                        }

                        // Column for text
                        Column(
                            modifier = Modifier
                                .weight(3f)
                                .padding(start = 16.dp)
                        ) {
                            //Text(text = "Id_Pet: ${pet.id}")
                            Text(text = "Name: ${pet.name}")
                            //Text(text = "Especie: ${pet.species}")
                            Text(text = "Name: ${pet.species.joinToString(", ")}")
                            Text(text = "Genero: ${pet.gender}")
                            Text(text = "Age: ${pet.age}")
                            //Text(text = "Id_User: ${pet.ownerId}")
                        }
                    }
                }
            }
        }

        FloatingActionButton(
            onClick = { /* Acción del botón flotante */ },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add Icon")
        }
    }
}
