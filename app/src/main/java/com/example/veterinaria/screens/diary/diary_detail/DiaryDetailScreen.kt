package com.example.veterinaria.screens.diary.diary_detail

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DiaryDetailScreen(
    state: DiaryDetailState,
    addNewDiary: (String, String, String, String) -> Unit,
    updateDiary: (String, String, String, String) -> Unit,
) {
    var pet by remember(state.diary?.pet) { mutableStateOf(state.diary?.pet ?:"") }
    var date by remember(state.diary?.date) { mutableStateOf(state.diary?.date ?:"") }
    var hour by remember(state.diary?.hour) { mutableStateOf(state.diary?.hour ?:"") }
    var service by remember(state.diary?.service) { mutableStateOf(state.diary?.service ?:"") }
    var description by remember(state.diary?.description) { mutableStateOf(state.diary?.description ?:"") }

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
                value = pet,
                onValueChange = { pet = it },
                label = {
                    Text(text = "Mascota")
                }
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                value = date,
                onValueChange = { date = it },
                label = {
                    Text(text = "Fecha")
                }
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                value = hour,
                onValueChange = { hour = it },
                label = {
                    Text(text = "Hora")
                }
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                value = service,
                onValueChange = { service = it },
                label = {
                    Text(text = "Servicio")
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
                    color = Red,
                    textAlign = TextAlign.Center
                )
            )
        }

        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            if (state.diary?.id != null) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter),
                    onClick = {
                        updateDiary(pet, date, hour, service)
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Red
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
                        addNewDiary(pet, date, hour, service)
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Red
                    )
                ) {
                    Text(
                        text = "Add New Diary",
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun prevDetail() {
    DiaryDetailScreen(
        DiaryDetailState(),
        addNewDiary = { pet, hour, date, service -> },
        updateDiary = { pet, hour, date, service -> }
    )
}