package com.example.veterinaria.navegation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.veterinaria.screens.MascotaSplashScreen
import com.example.veterinaria.screens.login.MascotaLoginScreen
@ExperimentalMaterialApi
@Composable
fun MascotaNavegation(){
    val navController = rememberNavController()
    NavHost(navController = navController,
        startDestination = MascotaScreens.SplashScreen.name)
    {
        composable(MascotaScreens.SplashScreen.name){
            MascotaSplashScreen(navController = navController)
        }
        composable(MascotaScreens.LoginScreen.name){
            MascotaLoginScreen(navController = navController)
        }
        composable(MascotaScreens.MascotaHomeScreen.name){
            //Home(navController = navController)
        }

    }
}


