package com.example.veterinaria.pet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
//import com.example.petapp.R
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

@Composable
fun PetRegisterScreen(
    viewModel: PetRegisterViewModel = hiltViewModel(),
    openPetScreen: () -> Unit
) {
//    variables
    val genero: String by viewModel.genero.observeAsState(initial = "")
    val edad: String by viewModel.edad.observeAsState(initial = "")
    val raza: String by viewModel.raza.observeAsState(initial = "")
    val foto: String by viewModel.foto.observeAsState(initial = "")
    val isSubmitting: Boolean by viewModel.isSubmitting.observeAsState(initial = false)
    val scope = rememberCoroutineScope()
//    components

    Column(modifier = Modifier) {
        Spacer(modifier = Modifier.padding(16.dp))
        GeneroField(genero) { viewModel.onPetRegisterChanged(it, edad, raza, foto) }
        Spacer(modifier = Modifier.padding(16.dp))
        EdadField(edad) { viewModel.onPetRegisterChanged(genero, it, raza, foto) }
        Spacer(modifier = Modifier.padding(16.dp))
        RazaField(raza) { viewModel.onPetRegisterChanged(genero, edad, it, foto) }
        Spacer(modifier = Modifier.padding(16.dp))
        Button(onClick = {
            scope.launch {
                viewModel.submitPetRegister()
            }

//                Toast.makeText(context,"Presionaste en login con google",Toast.LENGTH_SHORT)
        }, enabled = !isSubmitting, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text(text = "Crear")
        }

        Button(onClick = {
            openPetScreen()
        },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Regresar")
        }
    }
}

@Composable
fun GeneroField(email: String, onTextFieldChanged: (String) -> Unit) {
    TextField(
        value = email,
        onValueChange = { onTextFieldChanged(it) },
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Genero") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        singleLine = true,
        maxLines = 1
    )
}

@Composable
fun EdadField(edad: String, onTextFieldChanged: (String) -> Unit) {
    TextField(
        value = edad,
        onValueChange = { onTextFieldChanged(it) },
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Edad") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        singleLine = true,
        maxLines = 1
    )
}

@Composable
fun RazaField(raza: String, onTextFieldChanged: (String) -> Unit) {
    TextField(
        value = raza,
        onValueChange = { onTextFieldChanged(it) },
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Raza") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        singleLine = true,
        maxLines = 1
    )
}