package com.example.veterinaria.ui.components.splash

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.veterinaria.ui.navegation.MascotaScreens
import kotlinx.coroutines.delay

@ExperimentalMaterialApi
@Composable
fun MascotaSplashScreen(navController: NavController) {


    LaunchedEffect(key1 = true) {
        delay(3500L)
        navController.navigate(MascotaScreens.LoginScreen.name)
    }
    /*
    val currentContext = LocalContext.current
    if (FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty()){
        navController.navigate(MascotaScreens.LoginScreen.name)
    }else{
        currentContext.startActivity(
            Intent(
                currentContext,
                MainActivity::class.java
            )
        )
    }*/


    val color = MaterialTheme.colors.primary
    Surface(
        modifier = Modifier
            .padding(15.dp)
            .size(330.dp),
        shape = CircleShape,
        color = Color.White,
        border = BorderStroke(width = 2.dp, color = color)
    ) {
        Column(
            modifier = Modifier
                .padding(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("PetCare",
                style = MaterialTheme.typography.h3,
                color = color.copy(alpha = 0.5f)
            )
            Spacer(modifier = Modifier.height(15.dp))
            Text("Lo que tu mascota Necesita",
                style = MaterialTheme.typography.h5,
                color = color)
        }
    }
}
@ExperimentalMaterialApi
@Preview(showBackground = true,showSystemUi = true)
@Composable
fun prevSplash() {
    val navController = rememberNavController()
    MascotaSplashScreen(navController)
}