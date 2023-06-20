package com.example.veterinaria.ui.screens.user

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.google.firebase.auth.FirebaseAuth

/*@Composable
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
}*/
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
            .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 60.dp)
    ) {
        filteredUserList.forEach { user ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    //.padding(16.dp)
                    .border(1.dp, color = Color.LightGray, shape = RoundedCornerShape(8.dp))
                    .background(color = Color.White)
                    //.shadow(4.dp, shape = RoundedCornerShape(8.dp))
            ) {
                val imageUrl = user.photo
                Box(
                    modifier = Modifier
                        .size(150.dp)
                        .align(Alignment.CenterHorizontally)
                        .clip(shape = RoundedCornerShape(8.dp))
                        .border(2.dp, color = Color.LightGray, shape = RoundedCornerShape(8.dp))
                        .background(color = Color.DarkGray)
                        .padding(16.dp)
                ) {
                    Image(
                        painter = rememberImagePainter(imageUrl),
                        contentDescription = "Veterinario seleccionado",
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))


                Column(modifier = Modifier.padding(16.dp)
                    .fillMaxSize()){
                    Text(
                        text = "UserId:",
                        style = TextStyle(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = user.userId,
                        style = TextStyle(fontSize = 18.sp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Email:",
                        style = TextStyle(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = user.email,
                        style = TextStyle(fontSize = 18.sp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Phone:",
                        style = TextStyle(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = user.phone,
                        style = TextStyle(fontSize = 18.sp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Password:",
                        style = TextStyle(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = user.password,
                        style = TextStyle(fontSize = 18.sp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "UserName:",
                        style = TextStyle(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = user.username,
                        style = TextStyle(fontSize = 18.sp)
                    )

                    Spacer(modifier = Modifier.height(30.dp))
                    Spacer(modifier = Modifier.weight(1f))

                    Button(
                        onClick = { },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                    ) {
                        Text(text = "Actualizar información")
                    }
                }



            }
        }


    }
}
