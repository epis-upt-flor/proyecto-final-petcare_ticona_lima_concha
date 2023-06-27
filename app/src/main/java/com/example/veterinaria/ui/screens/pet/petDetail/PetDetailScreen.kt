package com.example.veterinaria.ui.screens.pet.petDetail

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.veterinaria.R
import com.example.veterinaria.ui.components.ProgressBar
import com.example.veterinaria.util.Constants
import com.example.veterinaria.util.Response
import com.example.veterinaria.viewmodel.ImageViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlin.reflect.KFunction
import kotlin.reflect.KFunction6

@SuppressLint("SuspiciousIndentation")
@Composable
fun PetDetailScreen(
    state: PetDetailState,
    addNewPet: (String, Int,String,String, String, String, String) -> Unit,
    updatePets: (String,Int,String,String,String,String) -> Unit,
    viewModel2: ImageViewModel = hiltViewModel(),
) {
    var nombre by remember(state.pet?.name) { mutableStateOf(state.pet?.name ?: "") }
    var age by remember(state.pet?.age) { mutableStateOf(state.pet?.age ?: 0) }
    var breed by remember(state.pet?.breed) { mutableStateOf(state.pet?.breed ?: "") }
    var gender by remember(state.pet?.gender) { mutableStateOf(state.pet?.gender ?: "") }
    var photo by remember(state.pet?.photo) { mutableStateOf(state.pet?.photo ?: "") }

    // Definir una lista de especies de animales para el DropdownMenu
    val speciesOptions = listOf("Perro", "Gato", "Ave", "Otro")
    var species by remember(state.pet?.species) { mutableStateOf(state.pet?.species ?: "") }

    var expanded by remember { mutableStateOf(false) } // Estado para controlar si el menú está expandido
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { imageUri ->
        imageUri?.let {
            viewModel2.addImageToStorange(imageUri)
        }
    }
    val imageUrl by viewModel2.imageUrl.collectAsState()
    var obtainedImageUrl by remember { mutableStateOf<String?>(null) }
    // Método para manejar la selección de una especie
    val onSpeciesSelected: (String) -> Unit = { selectedSpecies ->
        species = selectedSpecies
        expanded = false
    }
    val breedOptions = listOf("Chihuahua", "Golden Retriever", "Rottweiler","Boxer","Pomerania")
    val genderOptions = listOf("Masculino", "Femenino")


    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    val userId = auth.currentUser?.uid

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
                value = age.toString(),
                onValueChange = { age = it.toIntOrNull() ?: 0 },
                label = {
                    Text(text = "Edad")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )

            OutlinedButton(
                onClick = { expanded = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            ) {
                Text(text = if (species.isNotEmpty()) species else "Seleccionar especie")
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                speciesOptions.forEach { option ->
                    DropdownMenuItem(onClick = { onSpeciesSelected(option) }) {
                        Text(text = option)
                    }
                }
            }

            DropdownComponent(
                label = "Raza",
                selectedItem = breed,
                options = breedOptions,
                onItemSelected = { selectedBreed ->
                    breed = selectedBreed
                }
            )

            // Dropdown para gender
            DropdownComponent(
                label = "Género",
                selectedItem = gender,
                options = genderOptions,
                onItemSelected = { selectedGender ->
                    gender = selectedGender
                }
            )

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

            /*dropDownMenu(species) { selectedSpecies ->
                species = selectedSpecies
            }*/
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
            if (state.pet?.id != null) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter),
                    onClick = {




                        val photo = "https://firebasestorage.googleapis.com/v0/b/mascota002-3b966.appspot.com/" +
                                "o/images%2Fpet%2Fpet_gm.png?alt=media&token=fbb5e816-b03a-4686-80e6-75a94ea79a30"
                        updatePets(nombre,age,species,breed,gender,photo)
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
                        text = "Add New Pet",
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

                                    val photo = "https://firebasestorage.googleapis.com/v0/b/mascota002-3b966.appspot.com/" +
                                            "o/images%2Fpet%2Fpet_gm.png?alt=media&token=fbb5e816-b03a-4686-80e6-75a94ea79a30"
                                    val petId = addNewPet(
                                        nombre,
                                        age,
                                        species,
                                        breed,
                                        gender,
                                        photo,
                                        userId.toString()
                                    )
                                    Log.d("state1", "Id de pet $petId")
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

@Composable
fun DropdownComponent(
    label: String,
    selectedItem: String,
    options: List<String>,
    onItemSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    OutlinedButton(
        onClick = { expanded = true },
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    ) {
        Text(text = if (selectedItem.isNotEmpty()) selectedItem else "Seleccionar $label")
    }

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        options.forEach { option ->
            DropdownMenuItem(onClick = {
                onItemSelected(option)
                expanded = false
            }) {
                Text(text = option)
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
