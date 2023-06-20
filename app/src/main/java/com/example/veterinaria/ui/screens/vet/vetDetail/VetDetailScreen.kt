package com.example.veterinaria.ui.screens.vet.vetDetail

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Checkbox
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.example.veterinaria.R
import com.example.veterinaria.data.model.Location
import com.example.veterinaria.data.model.Service
import com.example.veterinaria.ui.MainActivity
import com.example.veterinaria.ui.components.ProgressBar
import com.example.veterinaria.ui.screens.emergencia.UbicacionLiveData
import com.example.veterinaria.util.Constants
import com.example.veterinaria.util.Response
import com.example.veterinaria.viewmodel.ImageViewModel
import com.example.veterinaria.viewmodel.ServiceListViewModel
import com.google.android.gms.maps.model.LatLng
import kotlin.reflect.KFunction5


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun vetScreen(
    state: VetDetailState,
    addNewVet: KFunction5<String, String, Location, List<Service>, String, Unit>,
    ubicacionLiveData: UbicacionLiveData,
    viewModel2: ImageViewModel = hiltViewModel()
) {
    var nombre by remember(state.vet?.name) { mutableStateOf(state.vet?.name ?: "") }
    var telefono by remember(state.vet?.phone) { mutableStateOf(state.vet?.phone ?: "") }
    var ubicacion by remember { mutableStateOf<LatLng?>(null) }
    val viewModel: ServiceListViewModel = hiltViewModel()
    val serviceState  = viewModel.state.value
    var selectedServices by remember { mutableStateOf<List<Service>>(state.vet?.services ?: emptyList()) }

    val currentContext = LocalContext.current


    // +++++++++++++++++++++++++++++++++++ Agregando ubicacion actual

    val ubicacionActual by ubicacionLiveData.observeAsState()
    /*Text(
        text = "Permitir ubicacion actual: ${ubicacionActual?.latitude}, ${ubicacionActual?.longitude}",
        modifier = Modifier.padding(start = 8.dp)
    )*/
    val latitud = ubicacionActual?.latitude?.toDoubleOrNull() ?: 0.0
    val longitud = ubicacionActual?.longitude?.toDoubleOrNull() ?: 0.0
    ubicacion = LatLng(latitud, longitud)
    //****************************************************************

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ){imageUri->
        imageUri?.let {
            viewModel2.addImageToStorange(imageUri)
        }
    }
    val imageUrl by viewModel2.imageUrl.collectAsState()

    var obtainedImageUrl by remember { mutableStateOf<String?>(null) }


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
                value = nombre.take(35),
                onValueChange = { nombre = it.take(35) },
                label = { Text(text = "Nombre") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                ),
                maxLines = 1
            )


            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                value = telefono.take(9),
                onValueChange = { telefono = it.filter { it.isDigit() }.take(9) },
                label = {
                    Text(text = "Telefono")
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )




            // ---------------------------------- Agregando Servicios
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(serviceState.services) { service ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    ) {
                        Checkbox(
                            //checked = selectedServices.contains(service.name),
                            checked = selectedServices.contains(service),
                            onCheckedChange = { isChecked ->
                                if (isChecked) {
                                    //selectedServices = selectedServices + service.name
                                    selectedServices = selectedServices + service
                                } else {
                                    //selectedServices = selectedServices - service.name
                                    selectedServices = selectedServices - service
                                }
                            }
                        )
                        Text(
                            text = service.name,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.padding(vertical = 10.dp))
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {

                AbrirGaleria(
                    openGallery = {
                        galleryLauncher.launch(Constants.ALL_IMAGES)
                    }
                )
                AddImageToStorage(
                    addImageToDatabase = { downloadUrl ->
                        viewModel2.addImageToDatabase(downloadUrl)

                    },
                    onImageUrlObtained = { imageUrl ->
                        obtainedImageUrl = imageUrl
                    }

                )
            }
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


                var showDialog by remember { mutableStateOf(false) }

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter),
                    onClick = { showDialog = true },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Red
                    )
                ) {
                    Text(
                        text = "Add New Vet",
                        color = Color.White
                    )
                }

                if (showDialog) {
                    AlertDialog(
                        onDismissRequest = { showDialog = false },
                        title = { Text(text = "Confirmación") },
                        text = { Text(text = "¿Estás seguro de que quieres agregar una veterinaria?") },
                        confirmButton = {
                            Button(
                                onClick = {
                                    showDialog = false
                                    obtainedImageUrl?.let {
                                        addNewVet(
                                            nombre,
                                            telefono,
                                            Location(latitud, longitud),
                                            selectedServices,
                                            it
                                        )

                                        currentContext.startActivity(
                                            Intent(
                                                currentContext,
                                                MainActivity::class.java
                                            )
                                        )
                                    }


                                }
                            ) {
                                Text(text = "Aceptar")
                            }
                        },
                        dismissButton = {
                            Button(
                                onClick = { showDialog = false }
                            ) {
                                Text(text = "Cancelar")
                            }
                        }
                    )
                }
            }
        }
    }
}

private fun LocationUpdates(
    ubicacionLiveData: UbicacionLiveData,
    context: Context,
    requestSinglePermissionLauncher: ActivityResultLauncher<String>
) {
    if (estaPermitidaLaUbicacion(context)) {
        ubicacionLiveData.startLocationUpdates()
    } else {
        requestSinglePermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }
}

private fun estaPermitidaLaUbicacion(context: Context): Boolean {
    return ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
}


@Composable
fun AbrirGaleria(
    openGallery:()->Unit
) {
    Box(
        modifier = Modifier
            .size(100.dp)
            .clickable { openGallery() }
            .border(1.dp, Color.White)
            .background(Color.Gray)
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_whatsapp_background),
            contentDescription = "Open Gallery",
            modifier = Modifier
                .align(Alignment.Center)
                .size(48.dp),
            tint = Color.White
        )
    }
}
@Composable
fun AddImageToStorage(
    viewModel: ImageViewModel = hiltViewModel(),
    addImageToDatabase : (downloadUrl: Uri) -> Unit,
    onImageUrlObtained: (imageUrl: String?) -> Unit
) {
    when ( val addImageToStorageResponse = viewModel.addImageToStorangeResponse){
        is Response.Loading -> ProgressBar()
        is Response.Sucess -> addImageToStorageResponse.data?.let { downloadUrl ->
            LaunchedEffect(downloadUrl) {
                addImageToDatabase(downloadUrl)
                onImageUrlObtained(downloadUrl.toString())
            }
        }
        is Response.Failure -> print(addImageToStorageResponse.e)
    }
}
@Composable
fun Image_Screen(
    viewModel: ImageViewModel = hiltViewModel()
) {
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ){imageUri->
        imageUri?.let {
            viewModel.addImageToStorange(imageUri)
        }
    }
    val imageUrl by viewModel.imageUrl.collectAsState()

    var obtainedImageUrl by remember { mutableStateOf<String?>(null) }

    AbrirGaleria(
        openGallery = {
            galleryLauncher.launch(Constants.ALL_IMAGES)
        }
    )
    AddImageToStorage(
        addImageToDatabase = { downloadUrl ->
            viewModel.addImageToDatabase(downloadUrl)

        },
        onImageUrlObtained = { imageUrl ->
            obtainedImageUrl = imageUrl
        }

    )
    Text(text = imageUrl ?:"")
}