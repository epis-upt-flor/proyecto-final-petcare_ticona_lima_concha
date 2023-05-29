package com.example.veterinaria.navegation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.veterinaria.pet.PetRegisterScreen
import com.example.veterinaria.pet.PetScreen
import com.example.veterinaria.screens.emergencia.EmergenciaMapScreen
import com.example.veterinaria.screens.emergencia.EmergenciaScreen
import com.example.veterinaria.screens.home.HomeScreen
import com.example.veterinaria.screens.home.InformacionVeterinariaScreen
import com.example.veterinaria.screens.vet.VetDetailViewModel
import com.example.veterinaria.screens.vet.vetScreen

sealed class OpcionMenuInferior(val icono: ImageVector, val titulo: String, val ruta: String) {
    object HomeScreen: OpcionMenuInferior(Icons.Default.Home, "Home","master")
    object MascotaScreen: OpcionMenuInferior(Icons.Default.Pets, "Mascota", "mascota")
    object MascotaDetalleScreen: OpcionMenuInferior(Icons.Default.Details, "Detalle", "mascota_detalle_screen")
}

sealed class OpcionMenuSuperior(val icono: ImageVector, val titulo: String, val ruta: String) {
    object MapaVeterinariaScreen: OpcionMenuSuperior(Icons.Default.Map, "Veterinarias","mapa-veterinaria")
    object CitasScreen: OpcionMenuSuperior(Icons.Default.CalendarMonth, "Citas", "citas")
    object MisMascotasScreen: OpcionMenuSuperior(Icons.Default.Pets, "Mis Mascotas", "mis-mascotas")
    object CerrarSesion: OpcionMenuSuperior(Icons.Default.Close, "Cerrar Sesion", "cerrar-sesion")
    object Emergencia : OpcionMenuSuperior(Icons.Default.Warning, "Emergencia", "emergencia")
    object Veterinaria : OpcionMenuSuperior(Icons.Default.Details, "Vet", "add_vet")
}
@ExperimentalMaterialApi
@Composable
fun NavegacionUsuario(navController: NavHostController) {
    NavHost(navController = navController, startDestination = OpcionMenuInferior.HomeScreen.ruta) {

        composable(route=OpcionMenuSuperior.MapaVeterinariaScreen.ruta){
            InformacionVeterinariaScreen()
        }
        composable(route=OpcionMenuSuperior.CitasScreen.ruta){
            navegationDiary()
        }
        composable(route=OpcionMenuSuperior.Emergencia.ruta){
            EmergenciaScreen(navController)
        }
        composable(route=PetRoutes.EmergenciaMapRoute.name){
            EmergenciaMapScreen()
        }
        composable(route=OpcionMenuSuperior.CerrarSesion.ruta){
            //Todo Cerrar Sesion
        }
        composable(route=OpcionMenuSuperior.Veterinaria.ruta){
            val viewModel: VetDetailViewModel = hiltViewModel()
            val state = viewModel.state.value

            vetScreen(
                state = state,
                addNewVet = viewModel::addNewVet
            )
        }

        composable(route=OpcionMenuInferior.MascotaScreen.ruta){
            //Todo MostrarScreen
        }
        composable(OpcionMenuInferior.HomeScreen.ruta) {
            HomeScreen(navController)
        }
        composable(route = OpcionMenuInferior.MascotaDetalleScreen.ruta + "/{id}", arguments = listOf(navArgument("id"){
            type = NavType.StringType
        })){ backStackEntry ->
            backStackEntry.arguments?.getString("id")?.toInt()?.let {
                //Todo MostrarScreen
            }
        }

        //
        composable(route = OpcionMenuSuperior.MisMascotasScreen.ruta){
            PetScreen(openRegisterPetScreen = { navController.navigate(PetRoutes.RegisterScreenRoute.name) } )
        }
        composable(route = PetRoutes.RegisterScreenRoute.name){
            PetRegisterScreen(openPetScreen = {navController.navigate(OpcionMenuSuperior.MisMascotasScreen.ruta)})
        }
    }
}

enum class PetRoutes {
    RegisterScreenRoute,
    ListPetScreenRoute,
    EmergenciaMapRoute
}