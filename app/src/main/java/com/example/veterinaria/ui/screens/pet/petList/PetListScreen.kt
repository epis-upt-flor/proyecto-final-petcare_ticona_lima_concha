package com.example.veterinaria.ui.screens.pet.petList

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.veterinaria.data.model.Pet
import com.example.veterinaria.ui.screens.diary.diary_list.DiaryListItem
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.firebase.auth.FirebaseAuth
@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PetListScreen(
    state: PetListState,
    navigateToPetDetail: () -> Unit,
    isRefreshing: Boolean,
    refreshData: () -> Unit,
    onItemClick: (String) -> Unit,
) {
    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    val userId56 = auth.currentUser?.uid

    val filteredPetList = state.pets.filter { pet ->
        pet.ownerId == userId56
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 10.dp, top = 10.dp, end = 10.dp, bottom = 65.dp),
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToPetDetail,
                backgroundColor = Color.Red,
                contentColor = Color.White
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Icon")
            }
        }
    )
    {
        PetsList(
            state = state,
            isRefreshing = isRefreshing,
            refreshData = refreshData,
            onItemClick = onItemClick
        )

    }


    /*Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 60.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            filteredPetList.forEach { pet ->
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    elevation = 4.dp,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        // Column for image
                        Column(
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            val imageUrl = pet.photo
                            Image(
                                painter = rememberImagePainter(imageUrl),
                                contentDescription = "Mascota seleccionada",
                                modifier = Modifier
                                    .size(100.dp)
                            )
                        }

                        // Column for text
                        Column(
                            modifier = Modifier
                                .weight(3f)
                                .padding(start = 16.dp)
                        ) {
                            //Text(text = "Id_Pet: ${pet.id}")
                            Text(text = "Name: ${pet.name}")
                            //Text(text = "Especie: ${pet.species}")
                            //Text(text = "Name: ${pet.species.joinToString(", ")}")
                            Text(text = "Genero: ${pet.gender}")
                            Text(text = "Age: ${pet.age}")
                            //Text(text = "Id_User: ${pet.ownerId}")
                        }
                    }
                }
            }
        }

        FloatingActionButton(
            onClick = navigateToPetDetail,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add Icon")
        }
    }*/
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PetsList(
    state: PetListState,
    isRefreshing: Boolean,
    refreshData: () -> Unit,
    onItemClick: (String) -> Unit,
) {

    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    val userId56 = auth.currentUser?.uid

    val filteredPetList = state.pets.filter { pet ->
        pet.ownerId == userId56
    }
    Box(
        modifier = Modifier.fillMaxSize(),
    ){
        //refrescar pantalla
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing),
            onRefresh = refreshData
        ){
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                //filteredPetList.forEach{
                    items(
//                        items = state.pets
                    items = state.pets.filter { pet ->
                        pet.ownerId == userId56
                    }
                    )
                    {pet->

                        var isDeleted by remember { mutableStateOf(false) }
                        val dismissState = rememberDismissState(
                            confirmStateChange = {
                                Log.d("PetList", "Dismiss value: ${it.name}")
                                if(it == DismissValue.DismissedToEnd) isDeleted = !isDeleted
                                it != DismissValue.DismissedToEnd
                            }
                        )

                        SwipeToDismiss(
                            state = dismissState,
                            directions = setOf(DismissDirection.StartToEnd),
                            dismissThresholds = {
                                FractionalThreshold(0.5f)
                            },
                            background = {
                                val direction = dismissState.dismissDirection ?:  return@SwipeToDismiss
                                val color by animateColorAsState(
                                    when(dismissState.targetValue) {
                                        DismissValue.Default -> Color.LightGray
                                        DismissValue.DismissedToEnd -> Color.Red
                                        DismissValue.DismissedToStart -> Color.Red
                                    }
                                )
                                val alignment = when (direction) {
                                    DismissDirection.StartToEnd -> Alignment.CenterStart
                                    DismissDirection.EndToStart -> Alignment.CenterEnd
                                }
                                val icon = when (direction) {
                                    DismissDirection.StartToEnd -> Icons.Default.Delete
                                    DismissDirection.EndToStart -> Icons.Default.Delete
                                }
                                val scale by animateFloatAsState(
                                    if (dismissState.targetValue == DismissValue.Default) 0.75f else 1f
                                )

                                Box(
                                    Modifier
                                        .fillMaxSize()
                                        .background(color)
                                        .padding(horizontal = 20.dp),
                                    contentAlignment = alignment
                                ) {
                                    Icon(
                                        icon,
                                        contentDescription = "Localized description",
                                        modifier = Modifier.scale(scale)
                                    )
                                }
                            }
                        ) {
                            if(isDeleted) {
                                // TODO("DELETE BOOK")
                            } else {
                                PetListItem(pet , onItemClick = onItemClick)
                            }
                        }
                    }
//                }
                /*items(
                    items = state.pets
                )
                {pet->

                    var isDeleted by remember { mutableStateOf(false) }
                    val dismissState = rememberDismissState(
                        confirmStateChange = {
                            Log.d("PetList", "Dismiss value: ${it.name}")
                            if(it == DismissValue.DismissedToEnd) isDeleted = !isDeleted
                            it != DismissValue.DismissedToEnd
                        }
                    )

                    SwipeToDismiss(
                        state = dismissState,
                        directions = setOf(DismissDirection.StartToEnd),
                        dismissThresholds = {
                            FractionalThreshold(0.5f)
                        },
                        background = {
                            val direction = dismissState.dismissDirection ?:  return@SwipeToDismiss
                            val color by animateColorAsState(
                                when(dismissState.targetValue) {
                                    DismissValue.Default -> Color.LightGray
                                    DismissValue.DismissedToEnd -> Color.Red
                                    DismissValue.DismissedToStart -> Color.Red
                                }
                            )
                            val alignment = when (direction) {
                                DismissDirection.StartToEnd -> Alignment.CenterStart
                                DismissDirection.EndToStart -> Alignment.CenterEnd
                            }
                            val icon = when (direction) {
                                DismissDirection.StartToEnd -> Icons.Default.Delete
                                DismissDirection.EndToStart -> Icons.Default.Delete
                            }
                            val scale by animateFloatAsState(
                                if (dismissState.targetValue == DismissValue.Default) 0.75f else 1f
                            )

                            Box(
                                Modifier
                                    .fillMaxSize()
                                    .background(color)
                                    .padding(horizontal = 20.dp),
                                contentAlignment = alignment
                            ) {
                                Icon(
                                    icon,
                                    contentDescription = "Localized description",
                                    modifier = Modifier.scale(scale)
                                )
                            }
                        }
                    ) {
                        if(isDeleted) {
                            // TODO("DELETE BOOK")
                        } else {
                            PetListItem(pet , onItemClick = onItemClick)
                        }
                    }
                }*/
            }
        }

        if (state.isLoading){
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}

@Composable
fun PetListItem(pet: Pet, onItemClick: (String) -> Unit) {
    Card(
        elevation = 0.dp
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .clickable {
                    onItemClick(pet.id)
                }
        ){
            val imageUrl = pet.photo
            Image(
                painter = rememberImagePainter(imageUrl),
                contentDescription = "Mascota seleccionada",
                modifier = Modifier
                    .size(100.dp)
            )
            /*Image(
                painter = rememberImagePainter(""),
                contentDescription = "",
                modifier = Modifier
                    .width(100.dp)
                    .height(160.dp)
                    .padding(8.dp)
            )*/

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(text = "Name: ${pet.name}")
                //Text(text = "Especie: ${pet.species}")
                //Text(text = "Name: ${pet.species.joinToString(", ")}")
                Text(text = "Genero: ${pet.gender}")
                Text(text = "Age: ${pet.age}")
            }
        }
    }
}
