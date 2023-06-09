package com.example.veterinaria.ui.screens.vet.vetDetail

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
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
import androidx.compose.material.TextFieldDefaults
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
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontVariation.weight
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.veterinaria.R
import com.example.veterinaria.data.model.Emergency
import com.example.veterinaria.data.model.Location
import com.example.veterinaria.data.model.RequestDetailState
import com.example.veterinaria.data.model.Service
import com.example.veterinaria.ui.MainActivity
import com.example.veterinaria.ui.components.ProgressBar
import com.example.veterinaria.ui.screens.emergencia.UbicacionLiveData
import com.example.veterinaria.ui.theme.DeepBlue
import com.example.veterinaria.ui.theme.Purple500
import com.example.veterinaria.ui.theme.TextWhite
import com.example.veterinaria.util.Constants
import com.example.veterinaria.util.Response
import com.example.veterinaria.viewmodel.EmergencyListViewModel
import com.example.veterinaria.viewmodel.ImageViewModel
import com.example.veterinaria.viewmodel.ServiceListViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.FirebaseAuth
import kotlin.reflect.KFunction7

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun vetScreen(
    state: VetDetailState,
    addNewVet: KFunction7<String, String, String, Location, List<Service>, List<Emergency>, String, String>,
    state5: RequestDetailState,
    addRequest: (String, String)-> Unit,
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
    var vetId by remember { mutableStateOf("") }

    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    val userId56 = auth.currentUser?.uid

    val currentContext = LocalContext.current
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
            .padding(0.dp, 0.dp, 0.dp, 55.dp)
        //.background(DeepBlue)
        ,

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
            //.padding(horizontal = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                //.padding(horizontal = 16.dp)
                ,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                //Text("Screen ${currentScreen.ordinal + 1}")
                Spacer(modifier = Modifier.height(16.dp))
                when (currentScreen) {
                    SurveyScreenState.SCREEN1 -> {

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .fillMaxSize()
                                //.background(DeepBlue)
                                .weight(0.15f),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            //Text("Únete a nuestra red de veterinarias y llega a más usuarios, brindando cuidado y bienestar animal.")
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 40.dp)
                                    .padding(top = 20.dp),
                                text = "Únete a nuestra red de veterinarias y llega a más usuarios, brindando cuidado y bienestar animal.",
                                fontSize = MaterialTheme.typography.subtitle1.fontSize,
                                fontWeight = FontWeight.Medium,
                                textAlign = TextAlign.Center
                            )
                        }



                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                //.background(DeepBlue)
                                .weight(0.40f),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(R.drawable.vetss),
                                contentDescription = "Imagen de tu veterinaria"
                            )
                        }



                        /*Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(DeepBlue)
                                .padding(16.dp,16.dp,16.dp,32.dp,)
                                .weight(0.25f),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("Completa tus datos correctamente y agrega una foto de tu veterinaria.")
                        }*/

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .fillMaxSize()
                                //.background(DeepBlue)
                                .weight(0.45f),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            //Text("Únete a nuestra red de veterinarias y llega a más usuarios, brindando cuidado y bienestar animal.")
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 40.dp)
                                //.padding(top = 20.dp)
                                ,
                                text = "Completa tus datos correctamente y agrega una foto de tu veterinaria.",
                                fontSize = MaterialTheme.typography.subtitle1.fontSize,
                                fontWeight = FontWeight.Medium,
                                textAlign = TextAlign.Center
                            )
                        }




                    }
                    SurveyScreenState.SCREEN2 -> {


                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .fillMaxSize()
                                //.background(DeepBlue)
                                .weight(0.25f),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            //Text("Únete a nuestra red de veterinarias y llega a más usuarios, brindando cuidado y bienestar animal.")
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 40.dp)
                                    .padding(top = 20.dp),
                                text = "Completa los siguientes datos",
                                fontSize = MaterialTheme.typography.subtitle1.fontSize,
                                fontWeight = FontWeight.Medium,
                                textAlign = TextAlign.Center
                            )
                        }

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .fillMaxSize()
                                //.background(DeepBlue)
                                .weight(0.50f),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
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
                                maxLines = 1,
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    backgroundColor = Color.White // Color de fondo blanco
                                )
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
                                maxLines = 1,
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    backgroundColor = Color.White // Color de fondo blanco
                                )
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
                                maxLines = 1,
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    backgroundColor = Color.White // Color de fondo blanco
                                )
                            )
                        }

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .fillMaxSize()
                                //.background(DeepBlue)
                                .weight(0.25f),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                        }

                    }
                    SurveyScreenState.SCREEN3 -> {

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .fillMaxSize()
                                //.background(DeepBlue)
                                .weight(0.25f),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            //Text("Únete a nuestra red de veterinarias y llega a más usuarios, brindando cuidado y bienestar animal.")
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 40.dp)
                                //.padding(top = 20.dp)
                                ,
                                text = "Selecciona los servicios de tu Veterinaria.",
                                fontSize = MaterialTheme.typography.subtitle1.fontSize,
                                fontWeight = FontWeight.Medium,
                                textAlign = TextAlign.Center
                            )
                        }


                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .fillMaxSize()
                                //.background(DeepBlue)
                                .weight(0.50f),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
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

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .fillMaxSize()
                                //.background(DeepBlue)
                                .weight(0.25f),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                        }


                    }
                    SurveyScreenState.SCREEN4 -> {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .fillMaxSize()
                                //.background(DeepBlue)
                                .weight(0.15f),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            //Text("Únete a nuestra red de veterinarias y llega a más usuarios, brindando cuidado y bienestar animal.")
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 40.dp)
                                //.padding(top = 20.dp)
                                ,
                                text = "Selecciona las emergencias que puedas atender.",
                                fontSize = MaterialTheme.typography.subtitle1.fontSize,
                                fontWeight = FontWeight.Medium,
                                textAlign = TextAlign.Center
                            )
                        }

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .fillMaxSize()
                                //.background(DeepBlue)
                                .weight(0.60f),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
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

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .fillMaxSize()
                                //.background(DeepBlue)
                                .weight(0.15f),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                        }



                    }
                    SurveyScreenState.SCREEN5 -> {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .fillMaxSize()
                                //.background(DeepBlue)
                                .weight(0.25f),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            //Text("Únete a nuestra red de veterinarias y llega a más usuarios, brindando cuidado y bienestar animal.")
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 40.dp)
                                //.padding(top = 20.dp)
                                ,
                                text = "Agrega una Imagen imagen o logo de tu veterinaria",
                                fontSize = MaterialTheme.typography.subtitle1.fontSize,
                                fontWeight = FontWeight.Medium,
                                textAlign = TextAlign.Center
                            )
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
                        },
                        modifier = Modifier.weight(0.5f),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Purple500 // Cambiar el color de fondo del botón
                        )
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
                            Modifier.weight(0.5f)

                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Purple500 // Cambiar el color de fondo del botón
                        )
                    ) {
                        Text("Next")
                    }
                }else{

                    if (state.error.isNotBlank()) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth(),
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
                                modifier = Modifier.weight(0.5f),
                                onClick = { showDialog = true },
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color.Red
                                )
                            ) {
                                Text(
                                    text = "Enviar Solicitud",
                                    color = Color.White
                                )
                            }

                            if (showDialog) {
                                AlertDialog(
                                    onDismissRequest = { showDialog = false },
                                    title = { Text(text = "Confirmación") },
                                    text = { Text(text = "¿Estás seguro de que quieres enviar una solicitud?") },
                                    confirmButton = {
                                        Button(
                                            onClick = {
                                                showDialog = false

                                                vetId = addNewVet(
                                                    nombre,
                                                    telefono,
                                                    address,
                                                    Location(latitud, longitud),
                                                    selectedServices,
                                                    selectedAccident,
                                                    obtainedImageUrl.toString()
                                                ).toString()

                                                if (userId56 != null) {
                                                    addRequest(userId56,vetId)
                                                }
                                                currentContext.startActivity(
                                                    Intent(
                                                        currentContext,
                                                        MainActivity::class.java
                                                    )
                                                )
                                            },Modifier.weight(1f)
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

