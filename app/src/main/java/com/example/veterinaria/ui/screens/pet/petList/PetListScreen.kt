package com.example.veterinaria.ui.screens.pet.petList

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(text = "Id_Actual_: ${userId56}")
        Text(text = "--------------------")

        filteredPetList.forEach { pet ->
            Text(text = "userId: ${pet.id}")
            Text(text = "name: ${pet.name}")
            Text(text = "age: ${pet.age}")
            Text(text = "age: ${pet.ownerId}")
            /*
            val imageUrl = pet.photo
            Image(
                painter = rememberImagePainter(imageUrl),
                contentDescription = "Mascota seleccionada",
                modifier = Modifier.size(100.dp).align(Alignment.CenterHorizontally)
            )*/

        }

    }
}