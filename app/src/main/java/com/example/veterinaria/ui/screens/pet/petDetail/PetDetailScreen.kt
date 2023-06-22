package com.example.veterinaria.ui.screens.pet.petDetail

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.google.firebase.auth.FirebaseAuth
import kotlin.reflect.KFunction
import kotlin.reflect.KFunction6

@SuppressLint("SuspiciousIndentation")
@Composable
fun PetDetailScreen(
    state: PetDetailState,

    //addNewPet: KFunction7<String, Int, String, String, String, String, String>,
    addNewPet: (String, Int,String,String, String, String, String) -> Unit,
    updatePets: (String,Int,String,String,String,String) -> Unit
//    addNewPet: (String, Int,String,String, String, String, String) -> Unit,
//    addNewPet: KFunction4<String, Int, List<String>, String, Unit>,
//    state3: UserDetailState,
//    updateUserPets: KFunction1<List<String>, Unit>
//    updateUserPets: (String)->Unit
//    viewModel: ImageViewModel = hiltViewModel(),
) {
    var nombre by remember(state.pet?.name) { mutableStateOf(state.pet?.name ?: "") }
    var age by remember(state.pet?.age) { mutableStateOf(state.pet?.age ?: 0) }
    //var species by remember { mutableStateOf(state.pet?.species ?: emptyList()) }
    //var breed by remember(state.pet?.breed) { mutableStateOf(state.pet?.breed ?: emptyList()) }
    //var species by remember(state.pet?.species) { mutableStateOf(state.pet?.species ?: "") }
    var breed by remember(state.pet?.breed) { mutableStateOf(state.pet?.breed ?: "") }
    var gender by remember(state.pet?.gender) { mutableStateOf(state.pet?.gender ?: "") }
    var photo by remember(state.pet?.photo) { mutableStateOf(state.pet?.photo ?: "") }

    // Definir una lista de especies de animales para el DropdownMenu
    val speciesOptions = listOf("Perro", "Gato", "Ave", "Otro")
    var species by remember(state.pet?.species) { mutableStateOf(state.pet?.species ?: "") }

    var expanded by remember { mutableStateOf(false) } // Estado para controlar si el menú está expandido

    // Método para manejar la selección de una especie
    val onSpeciesSelected: (String) -> Unit = { selectedSpecies ->
        species = selectedSpecies
        expanded = false
    }
    val breedOptions = listOf("Opción 1", "Opción 2", "Opción 3")
    val genderOptions = listOf("Masculino", "Femenino", "Otro")


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
fun dropDownMenu(selectedItems: List<String>, onItemsSelected: (List<String>) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val suggestions = listOf("Perro", "Gato", "Ave", "Otro")

    var selectedText by remember { mutableStateOf("") }

    var textfieldSize by remember { mutableStateOf(Size.Zero) }

    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Column() {
        OutlinedTextField(
            value = selectedText,
            onValueChange = { selectedText = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .onGloballyPositioned { coordinates ->
                    //This value is used to assign to the DropDown the same width
                    textfieldSize = coordinates.size.toSize()
                },
            label = { Text("Especie") },
            trailingIcon = {
                Icon(icon, "contentDescription",
                    Modifier.clickable { expanded = !expanded })
            }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current) { textfieldSize.width.toDp() })
        ) {
            suggestions.forEach { label ->
                DropdownMenuItem(onClick = {
                    selectedText = label
                    expanded = false
                    onItemsSelected(listOf(label))
                }) {
                    Text(text = label)
                }
            }
        }
    }

}