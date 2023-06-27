package com.example.veterinaria.ui.screens.vet

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.veterinaria.R
import com.example.veterinaria.data.model.Veterinary
import com.example.veterinaria.ui.screens.vet.vetList.VetListState

@ExperimentalMaterialApi
@Composable
fun VeterinaryList(
    state: VetListState,
    isRefreshing: Boolean,
    refreshData: () -> Unit,
    onItemClick: (String) -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(
                items = state.vet
            ) {veterinary ->
                VeterinaryListItem(veterinary, onItemClick = onItemClick)
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun VeterinaryListItem(
    veterinary: Veterinary,
    onItemClick: (String) -> Unit,
) {
    Card(
        elevation = 0.dp,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .clickable {
                onItemClick(veterinary.id)
            }
            .padding(8.dp)
            .shadow(4.dp) // Aplica el sombreado al Card
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
//                .background(Color.White)
//                .clickable {
//                    onItemClick(veterinary.id)
//                }
        ) {
            Image(
                painter = rememberImagePainter(veterinary.veterinary_logo),
                contentDescription = "",
                modifier = Modifier
                    .width(50.dp)
                    .height(50.dp)
                    .padding(8.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = veterinary.name,
                    color = Color.Black
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    VeterinaryStateTag(veterinary.state)
                }



            }
        }
    }
}
@Composable
fun VeterinaryStateTag(state: String) {
    val color = when (state) {
        "Activo" -> R.color.purple_200
        "Inactivo" -> R.color.teal_200
        "Suspendido" -> R.color.purple_700
        else -> R.color.purple_200
    }
    ChipView(state, colorResource(id = color))
}
@Composable
fun ChipView(state: String, colorResource: Color) {
    Box(
        modifier = Modifier
            .wrapContentWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(colorResource.copy(alpha = .08f))
    ) {
        Text(
            text = state,
            modifier = Modifier.padding(12.dp, 6.dp, 12.dp, 6.dp),
            style = typography.caption,
            color = colorResource
        )
    }
}


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalMaterialApi
@Composable
fun VeterinaryListScreen(
    state: VetListState,
    isRefreshing: Boolean,
    refreshData: () -> Unit,
    onItemClick: (String) -> Unit,
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 10.dp, top = 10.dp, end = 10.dp, bottom = 65.dp),
    ) {
        VeterinaryList(
            state = state,
            isRefreshing = isRefreshing,
            refreshData = refreshData,
            onItemClick = onItemClick
        )
    }
}