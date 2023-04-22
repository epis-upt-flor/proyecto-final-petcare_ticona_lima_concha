package com.example.veterinaria.screens.diary.diary_list

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.veterinaria.modelos.Diary
import com.example.veterinaria.modelos.Pet

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalMaterialApi
@Composable
fun DiaryListScreen(
    state: DiaryListState,
    navigateToDiaryDetail: () -> Unit,
    isRefreshing: Boolean,
    refreshData: () -> Unit,
    onItemClick: (String) -> Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 10.dp, top = 10.dp, end = 10.dp, bottom = 65.dp),
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToDiaryDetail,
                backgroundColor = Red,
                contentColor = Color.White
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Icon")
            }
        }
    ) {
        DiaryList(
            state = state,
            isRefreshing = isRefreshing,
            refreshData = refreshData,
            onItemClick = onItemClick
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview( showSystemUi = true)
@Composable
fun prevList() {
    val list = listOf(
        Diary("",
            "GaryImg",
            "21-04-23",
            "2:00 pm",
            "Vacunacion",
            ""
        )
    )
    DiaryListScreen(DiaryListState(
        true,
        list,
        ""
    ),
        {},
        true,{},
        onItemClick = {})
}