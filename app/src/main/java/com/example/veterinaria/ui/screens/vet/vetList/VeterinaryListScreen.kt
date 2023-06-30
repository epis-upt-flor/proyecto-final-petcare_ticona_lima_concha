package com.example.veterinaria.ui.screens.vet.vetList

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.veterinaria.R
import com.example.veterinaria.ui.screens.vet.vetDetail.VetDetailState
import com.example.veterinaria.ui.theme.DeepBlue
import com.example.veterinaria.ui.theme.blueBG
import com.example.veterinaria.ui.theme.blueText
import com.google.firebase.auth.FirebaseAuth

@Composable
fun VeterinaryDetailScreen(
    state: VetDetailState,
    updateVeterinary: (String) -> Unit,
) {
    var vetid by remember(state.vet?.id) { mutableStateOf(state.vet?.id ?: "") }

    val name by remember(state.vet?.name) { mutableStateOf(state.vet?.name ?: "") }
    val address by remember(state.vet?.address) { mutableStateOf(state.vet?.address ?: "") }
    val phone by remember(state.vet?.phone) { mutableStateOf(state.vet?.phone ?: "") }
    val services by remember(state.vet?.services) { mutableStateOf(state.vet?.services ?: "") }
    val logo by remember(state.vet?.veterinary_logo) {
        mutableStateOf(
            state.vet?.veterinary_logo ?: ""
        )
    }
    var veterinaryState by remember(state.vet?.state) { mutableStateOf(state.vet?.state ?: "") }

    val dropdownValues = listOf("Activo", "Inactivo", "Suspendido")
    var isDropdownExpanded by remember { mutableStateOf(false) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(blueBG),
    ) {

        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            painter = rememberAsyncImagePainter(logo),
            alignment = Alignment.CenterStart,
            contentDescription = "",
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.padding(vertical = 8.dp))
        Text(
            text = "Nombre: $name",
            modifier = Modifier
                .fillMaxWidth(),
            color = blueText,
            style = MaterialTheme.typography.h6
        )
        Spacer(modifier = Modifier.padding(vertical = 8.dp))
        val locationIcon: Painter =
            painterResource(id = R.drawable.baseline_location_on_24)

        Row{
            Text(
                text = "Direccion: $address",
                modifier = Modifier
                    .fillMaxWidth(),
                color = blueText,
                style = MaterialTheme.typography.h6
            )
            Icon(
                painter = locationIcon,
                contentDescription = null,
                modifier = Modifier.size(16.dp, 16.dp),
                tint = Red
            )
        }
        Spacer(modifier = Modifier.padding(vertical = 8.dp))
        Text(
            text =  "Telefono: $phone",
            modifier = Modifier
                .fillMaxWidth(),
            color = blueText,
            style = MaterialTheme.typography.h6
        )
        Spacer(modifier = Modifier.padding(vertical = 8.dp))
        Text(
            text =  "Estado:",
            modifier = Modifier
                .fillMaxWidth(),
            color = blueText,
            style = MaterialTheme.typography.h6
        )
        Spacer(modifier = Modifier.padding(vertical = 8.dp))
        OutlinedButton(
            onClick = { isDropdownExpanded = true },
            modifier = Modifier.fillMaxWidth().padding(16.dp, 0.dp, 16.dp, 0.dp),
        ) {
            Text(text = veterinaryState,color = blueText)
        }
        DropdownMenu(
            modifier = Modifier
                .fillMaxWidth().background(blueText),
            expanded = isDropdownExpanded,
            onDismissRequest = { isDropdownExpanded = false },
        ) {
            dropdownValues.forEach { value ->
                DropdownMenuItem(
                    modifier = Modifier
                        .fillMaxWidth().padding(16.dp, 0.dp, 16.dp, 0.dp),
                    onClick = {
                        veterinaryState = value
                        isDropdownExpanded = false
                    }
                ) {
                    Text(text = value,color = Color.White)
                }
            }
        }
        Spacer(modifier = Modifier.padding(vertical = 16.dp))
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
            CircularProgressIndicator()
        } else {
            if (state.vet?.id != null) {

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .padding(16.dp, 0.dp, 16.dp, 0.dp),
                    onClick = {
                        updateVeterinary(veterinaryState)
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = blueText
                    )
                ) {
                    Text(
                        text = "Aceptar solicitud",
                        color = Color.White
                    )
                }
            }
        }
    }
}

