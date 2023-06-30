package com.example.veterinaria.ui.screens.request

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.veterinaria.R
import com.example.veterinaria.data.model.Pet
import com.example.veterinaria.data.model.Veterinary
import com.example.veterinaria.ui.screens.pet.petList.GenderTag
import com.example.veterinaria.ui.screens.vet.vetList.VetListState
import com.example.veterinaria.ui.theme.blueBG
import com.example.veterinaria.ui.theme.blueText
import com.example.veterinaria.ui.theme.card


@ExperimentalMaterialApi
@Composable
fun VeterinaryList(
    state: VetListState,
    isRefreshing: Boolean,
    refreshData: () -> Unit,
    onItemClick: (String) -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize()
            .background(blueBG),
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
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = { onItemClick(veterinary.id) }),
        elevation = 0.dp,
//        backgroundColor = MaterialTheme.colors.onSurface
        backgroundColor = card
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            val image = veterinary.veterinary_logo
            Image(
                modifier = Modifier
                    .size(80.dp, 80.dp)
                    .clip(RoundedCornerShape(16.dp)),
                painter = rememberAsyncImagePainter(image),
                alignment = Alignment.CenterStart,
                contentDescription = "",
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.align(Alignment.CenterVertically)) {
                Text(
                    text = veterinary.name,
                    modifier = Modifier.padding(0.dp, 0.dp, 12.dp, 0.dp),
                    color = blueText,
                    fontWeight = FontWeight.Bold,
                    style = typography.subtitle1
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = buildString {
                        append(veterinary.phone)
                        append(" | ")
                        append(veterinary.state)
                    },
                    modifier = Modifier.padding(0.dp, 0.dp, 12.dp, 0.dp),
                    color = blueText,
                    style = typography.caption
                )

                Row(verticalAlignment = Alignment.Bottom) {

                    val location: Painter = painterResource(id = R.drawable.baseline_location_on_24)

                    Icon(
                        painter = location,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp, 16.dp),
                        tint = Color.Red
                    )

                    Text(
                        text = veterinary.address,
                        modifier = Modifier.padding(8.dp, 12.dp, 12.dp, 0.dp),
                        color = blueText,
                        style = typography.caption
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                VeterinaryStateTag(veterinary.state)
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