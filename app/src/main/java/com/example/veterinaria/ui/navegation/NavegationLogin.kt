package com.example.veterinaria.ui.navegation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.veterinaria.ui.components.splash.DestinationSplash
import com.example.veterinaria.ui.components.splash.WelcomeScreen
import com.example.veterinaria.ui.screens.login.LoginScreen
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalAnimationApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun MascotaNavegation(){
    val navController = rememberNavController()
    NavHost(navController = navController,
        startDestination = DestinationSplash.Home.route)
    {
        composable(DestinationSplash.Home.route){
            WelcomeScreen(navController = navController)
        }
        composable(DestinationSplash.Profile.route){
            LoginScreen(navController = navController)
        }

    }
}

enum class MascotaScreens {
    SplashScreen,
    LoginScreen,
    MascotaHomeScreen
}


