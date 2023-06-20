package com.example.veterinaria.ui.screens.user

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.google.firebase.auth.FirebaseAuth

@Composable
fun UserListScreen(
    state: UserListState,
) {
    val auth: FirebaseAuth = FirebaseAuth.getInstance()
    val userId56 = auth.currentUser?.uid
    val filteredUserList = state.userList.filter { user ->
        user.userId == userId56
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(text = "Id_Actual_: $userId56")
        filteredUserList.forEach { user ->
            Text(text = "UserId: ${user.userId}")
            Text(text = "Email: ${user.email}")
            Text(text = "Phone: ${user.phone}")
            Text(text = "Password: ${user.password}")
            Text(text = "UserName: ${user.username}")
            val imageUrl = user.photo
            Image(
                painter = rememberImagePainter(imageUrl),
                contentDescription = "Veterinario seleccionado",
                modifier = Modifier.size(100.dp).align(Alignment.CenterHorizontally)
            )
        }
    }
}
