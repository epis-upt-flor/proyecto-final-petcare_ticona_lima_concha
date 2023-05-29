package com.example.veterinaria.screens.vet

import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter

@Composable
fun vetScreen(
    state: VetDetailState,
    addNewVet: (String, String, String, String) -> Unit
) {

    var nombre by remember(state.vet?.name) { mutableStateOf(state.vet?.name ?: "") }
    var telefono by remember(state.vet?.phone) { mutableStateOf(state.vet?.phone ?: "") }
    var ubicacion by remember(state.vet?.location) { mutableStateOf(state.vet?.location ?: "") }
    var servicios by remember(state.vet?.services) { mutableStateOf(state.vet?.services ?: "") }



    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 10.dp, top = 10.dp, end = 10.dp, bottom = 65.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                value = nombre,
                onValueChange = { nombre = it },
                label = {
                    Text(text = "Nombre")
                }
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                value = telefono,
                onValueChange = { telefono = it },
                label = {
                    Text(text = "Telefono")
                }
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                value = ubicacion,
                onValueChange = { ubicacion = it },
                label = {
                    Text(text = "Ubicacion")
                }
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                value = servicios,
                onValueChange = { servicios = it },
                label = {
                    Text(text = "Servicios")
                }
            )
        }

        if (state.error.isNotBlank()) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                text = state.error,
                style = TextStyle(
                    color = Color.Red,
                    textAlign = TextAlign.Center
                )
            )
        }

        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            if (state.vet?.id != null) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter),
                    onClick = {

                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Red
                    )
                ) {
                    Text(
                        text = "Update Diary",
                        color = Color.White
                    )
                }
            } else {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter),
                    onClick = {
                        addNewVet(nombre, telefono, ubicacion, servicios)
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Red
                    )
                ) {
                    Text(
                        text = "Add New Vet",
                        color = Color.White
                    )
                }
            }
        }
    }
    Spacer(modifier = Modifier.padding(vertical = 10.dp))
    Box(
        modifier = Modifier
            .fillMaxSize(), contentAlignment = Alignment.Center

    ) {
        MyAppImg()
    }


}
@SuppressLint("RememberReturnType")
@Composable
fun MyAppImg(){
    var selectedImage by remember {
        mutableStateOf<Uri?>(null)
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {uri ->  selectedImage = uri
    }

    maincontent(selectedImage) {
        launcher.launch("image/*")
    }
}

@Composable
fun maincontent(
    selectedImage: Uri? = null,
    onImagenClick:()-> Unit
){
    Column() {
        if (selectedImage == null){
            OutlinedButton(onClick =  onImagenClick ) {
                Text(text = "Selecciona un Imagen")
            }
        }else{
            Image(
                painter = rememberImagePainter(selectedImage),
                contentDescription = "Selected Imagen",
                modifier = Modifier.clickable{
                    onImagenClick()
                }
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun prevSc() {
    vetScreen(VetDetailState(), addNewVet = {nombre,telefono,ubicacion,servicios->})

}