package com.example.veterinaria.ui.screens.vet

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.veterinaria.data.model.Veterinary
import com.example.veterinaria.ui.screens.vet.vetDetail.VetDetailState
import com.example.veterinaria.ui.screens.vet.vetList.VetListState
import com.example.veterinaria.viewmodel.VetDetailViewModel


@Composable
fun VeterinaryDetailScreen(
    state: VetDetailState,
    updateVeterinary: (String) -> Unit,
) {
    var name by remember(state.vet?.name) { mutableStateOf(state.vet?.name ?: "") }
    var veterinaryState by remember(state.vet?.state) { mutableStateOf(state.vet?.state ?: "") }

    val dropdownValues = listOf("Activo", "Inactivo", "Suspendido")
    var isDropdownExpanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 10.dp, top = 10.dp, end = 10.dp, bottom = 65.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {

            OutlinedButton(
                onClick = { isDropdownExpanded = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = veterinaryState)
            }

            DropdownMenu(
                expanded = isDropdownExpanded,
                onDismissRequest = { isDropdownExpanded = false },
            ) {
                dropdownValues.forEach { value ->
                    DropdownMenuItem(
                        onClick = {
                            veterinaryState = value
                            isDropdownExpanded = false
                        }
                    ) {
                        Text(text = value)
                    }
                }
            }
        }

        if (state.error.isNotBlank()) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                text = state.error,
                style = TextStyle(
                    color = Red,
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
                        updateVeterinary(veterinaryState)
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Red
                    )
                ) {
                    Text(
                        text = "Actualizar veterinaria",
                        color = Color.White
                    )
                }
            } else {

            }
        }
    }
}

