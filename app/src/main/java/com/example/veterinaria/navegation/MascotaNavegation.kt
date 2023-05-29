package com.example.veterinaria.navegation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.veterinaria.screens.Splash.WelcomeScreen
import com.example.veterinaria.screens.login.MascotaLoginScreen
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalAnimationApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun MascotaNavegation(){
    val navController = rememberNavController()
    NavHost(navController = navController,
        //startDestination = MascotaScreens.SplashScreen.name)
        startDestination = DestinationSplash.Home.route)
    {
        composable(DestinationSplash.Home.route){
            WelcomeScreen(navController = navController)
        }
        composable(DestinationSplash.Profile.route){
            MascotaLoginScreen(navController = navController)
        }
        /*composable(MascotaScreens.LoginScreen.name){
            MascotaLoginScreen(navController = navController)
        }
        composable(MascotaScreens.MascotaHomeScreen.name){
            //Home(navController = navController)
        }*/

    }
}


