package com.example.veterinaria.ui.screens.vet.vetDetail

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.simulateHotReload
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.veterinaria.R
import com.example.veterinaria.data.model.Emergency
import com.example.veterinaria.data.model.Location
import com.example.veterinaria.data.model.Service
import com.example.veterinaria.ui.MainActivity
import com.example.veterinaria.ui.components.ProgressBar
import com.example.veterinaria.ui.screens.emergencia.UbicacionLiveData
import com.example.veterinaria.ui.theme.slightlyDeemphasizedAlpha
import com.example.veterinaria.util.Constants
import com.example.veterinaria.util.Response
import com.example.veterinaria.viewmodel.EmergencyListViewModel
import com.example.veterinaria.viewmodel.ImageViewModel
import com.example.veterinaria.viewmodel.ServiceListViewModel
import com.google.android.gms.maps.model.LatLng

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun vetScreen(
    state: VetDetailState,
    addNewVet: (String, String,String, Location, List<Service>, List<Emergency>, String) -> Unit,
    ubicacionLiveData: UbicacionLiveData,
    viewModel2: ImageViewModel = hiltViewModel(),
) {
    var nombre by remember(state.vet?.name) { mutableStateOf(state.vet?.name ?: "") }
    var telefono by remember(state.vet?.phone) { mutableStateOf(state.vet?.phone ?: "") }
    var address by remember(state.vet?.address) { mutableStateOf(state.vet?.address ?: "") }
    var ubicacion by remember { mutableStateOf<LatLng?>(null) }
    val viewModel: ServiceListViewModel = hiltViewModel()
    val viewModel3: EmergencyListViewModel = hiltViewModel()
    val serviceState = viewModel.state.value
    val accidentState = viewModel3.state.value
    var selectedServices by remember {
        mutableStateOf<List<Service>>(
            state.vet?.services ?: emptyList()
        )
    }
    var selectedAccident by remember {
        mutableStateOf<List<Emergency>>(
            state.vet?.emergency ?: emptyList()
        )
    }
    val currentContext = LocalContext.current
    // +++++++++++++++++++++++++++++++++++ Aggregation actual
    val ubicacionActual by ubicacionLiveData.observeAsState()
    val latitud = ubicacionActual?.latitude?.toDoubleOrNull() ?: 0.0
    val longitud = ubicacionActual?.longitude?.toDoubleOrNull() ?: 0.0
    ubicacion = LatLng(latitud, longitud)
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { imageUri ->
        imageUri?.let {
            viewModel2.addImageToStorange(imageUri)
        }
    }
    val imageUrl by viewModel2.imageUrl.collectAsState()
    var obtainedImageUrl by remember { mutableStateOf<String?>(null) }
    var currentScreen by remember { mutableStateOf(SurveyScreenState.SCREEN1) }

    val progress = animateFloatAsState(
        targetValue = currentScreen.ordinal.toFloat(),
        animationSpec = tween(durationMillis = 500)
    ).value / (SurveyScreenState.values().size - 1)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(1.dp, 1.dp, 1.dp, 60.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.padding(vertical = 2.dp))
        Text("${currentScreen.ordinal + 1}-5")
        Spacer(modifier = Modifier.padding(vertical = 1.dp))
        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier.fillMaxWidth()
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                //Text("Screen ${currentScreen.ordinal + 1}")
                Spacer(modifier = Modifier.height(16.dp))
                when (currentScreen) {
                    SurveyScreenState.SCREEN1 -> {
                        Text("Screen 1 Content")
                    }
                    SurveyScreenState.SCREEN2 -> {
                        Column {
                            Text("Screen 2 Content")
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
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                maxLines = 1
                            )
                            OutlinedTextField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 8.dp),
                                value = address.take(9),
                                onValueChange = { address = it.take(35) },
                                label = {
                                    Text(text = "Direccion")
                                },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                                maxLines = 1
                            )
                        }
                    }
                    SurveyScreenState.SCREEN3 -> {
                        Text("Screen 3 Content")
                        LazyColumn(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            items(serviceState.services) { service ->
                                Surface(
                                    modifier = Modifier.padding(bottom = 8.dp),
                                    shape = MaterialTheme.shapes.small,
                                    border = BorderStroke(
                                        width = 1.dp,
                                        color = MaterialTheme.colors.primary,
                                    )
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,

                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(bottom = 8.dp)
                                    ) {
                                        Text(
                                            text = service.name,
                                            modifier = Modifier
                                                .padding(start = 8.dp)
                                                .weight(1f),
                                            style = MaterialTheme.typography.h2
                                        )
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
                                    }
                                }
                            }
                        }
                    }
                    SurveyScreenState.SCREEN4 -> {
                        Text("Screen 4 Content")
                        LazyColumn(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            items(accidentState.emergency) { emergency ->

                                Surface(
                                    modifier = Modifier.padding(bottom = 8.dp),
                                    shape = MaterialTheme.shapes.small,
                                    border = BorderStroke(
                                        width = 1.dp,
                                        color = MaterialTheme.colors.primary,

                                        )
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(bottom = 8.dp)
                                    ) {
                                        Text(
                                            text = emergency.type,
                                            modifier = Modifier
                                                .padding(start = 8.dp)
                                                .weight(1f),
                                            style = MaterialTheme.typography.h2
                                        )
                                        Checkbox(
                                            checked = selectedAccident.contains(emergency),
                                            onCheckedChange = { isChecked ->
                                                if (isChecked) {
                                                    selectedAccident += emergency
                                                } else {
                                                    selectedAccident -= emergency
                                                }
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                    SurveyScreenState.SCREEN5 -> {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                //.background(Color.Green)
                                .weight(0.25f),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("Agrega una Imagen ")
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                //.background(Color.Blue)
                                .weight(0.50f)
                                .height(IntrinsicSize.Min),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            AbrirGaleria(
                                openGallery = {
                                    galleryLauncher.launch(Constants.ALL_IMAGES)
                                },
                                obtainedImageUrl
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
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                //.background(Color.White)
                                .weight(0.25f)
                        ) {

                        }

                    }
                }
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(bottom = 16.dp) // Espacio entre los botones y el borde inferior
            ) {
                if (currentScreen != SurveyScreenState.SCREEN1) {
                    Button(
                        onClick = {
                            currentScreen = SurveyScreenState.values()[
                                    (currentScreen.ordinal + SurveyScreenState.values().size - 1) %
                                            SurveyScreenState.values().size
                            ]
                        }
                    ) {
                        Text("Previous")
                    }
                }

                if (currentScreen != SurveyScreenState.SCREEN5){
                    Button(
                        onClick = {
                            currentScreen = SurveyScreenState.values()[
                                    (currentScreen.ordinal + 1) % SurveyScreenState.values().size
                            ]
                        },
                        enabled = currentScreen != SurveyScreenState.SCREEN5,
                        modifier = if (currentScreen == SurveyScreenState.SCREEN1) {
                            Modifier.weight(1f)
                        } else {
                            Modifier
                        }
                    ) {
                        Text("Next")
                    }
                }else{

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
                        CircularProgressIndicator(modifier = Modifier)
                    } else {
                        if (state.vet?.id != null) {
                            Button(
                                modifier = Modifier,
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
                                modifier = Modifier,
                                onClick = { showDialog = true },
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color.Red
                                )
                            ) {
                                Text(
                                    text = "Agregar Veterinaria",
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
                                                        address,
                                                        Location(latitud, longitud),
                                                        selectedServices,
                                                        selectedAccident,
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
        }
    }
}


@Composable
fun AbrirGaleria(
    openGallery: () -> Unit,
    imageUri: String?,
) {
    val hasPhoto = imageUri != null
    val iconResource = if (hasPhoto) {
        Icons.Filled.SwapHoriz
    } else {
        Icons.Filled.AddAPhoto
    }

    OutlinedButton(
        onClick = {
            openGallery()
        },
        shape = MaterialTheme.shapes.small,
        contentPadding = PaddingValues()
    ) {
        /*Column(
            modifier = Modifier
                .padding(horizontal = 86.dp, vertical = 74.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(modifier = Modifier
                    .wrapContentSize(Alignment.BottomCenter)
                    .padding(vertical = 26.dp),
                    verticalAlignment = Alignment.CenterVertically) {
                    if (hasPhoto) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(imageUri)
                                .crossfade(true)
                                .build(),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        PhotoDefaultImage(modifier = Modifier.fillMaxSize())
                    }
                }
                Row(
                    modifier = Modifier
                        .wrapContentSize(Alignment.BottomCenter)
                        .padding(vertical = 26.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = iconResource, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = stringResource(
                            id = if (hasPhoto) {
                                R.string.retake_photo
                            } else {
                                R.string.add_photo
                            }
                        )
                    )
                }
            }

        }*/

        Column(
            modifier = Modifier
                .padding(horizontal = 86.dp, vertical = 5.dp)
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    if (hasPhoto) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(imageUri)
                                .crossfade(true)
                                .build(),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        PhotoDefaultImage(modifier = Modifier.fillMaxSize())
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(vertical = 5.dp)
                ) {
                    Icon(imageVector = iconResource, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = stringResource(
                            id = if (hasPhoto) {
                                R.string.retake_photo
                            } else {
                                R.string.add_photo
                            }
                        )
                    )
                }
            }
        }


    }
}

@Composable
private fun PhotoDefaultImage(
    modifier: Modifier = Modifier,
    lightTheme: Boolean = LocalContentColor.current.luminance() < 0.5f,
) {
    val assetId = if (lightTheme) {
        R.drawable.ic_selfie_light
    } else {
        R.drawable.ic_selfie_dark
    }
    Image(
        painter = painterResource(id = assetId),
        modifier = modifier,
        contentDescription = null
    )
}
enum class SurveyScreenState {
    SCREEN1,
    SCREEN2,
    SCREEN3,
    SCREEN4,
    SCREEN5
}
@Composable
fun AddImageToStorage(
    viewModel: ImageViewModel = hiltViewModel(),
    addImageToDatabase: (downloadUrl: Uri) -> Unit,
    onImageUrlObtained: (imageUrl: String?) -> Unit,
) {
    when (val addImageToStorageResponse = viewModel.addImageToStorangeResponse) {
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

