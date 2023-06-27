package com.example.veterinaria.ui.navegation

import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.veterinaria.ui.screens.emergencia.EmergenciaMapScreen
import com.example.veterinaria.ui.screens.emergencia.EmergenciaScreen
import com.example.veterinaria.ui.screens.emergencia.UbicacionLiveData
import com.example.veterinaria.ui.screens.home.HomeScreen
import com.example.veterinaria.ui.screens.pet.petDetail.PetDetailScreen
import com.example.veterinaria.ui.screens.pet.petList.PetListScreen
import com.example.veterinaria.ui.screens.user.UserListScreen
import com.example.veterinaria.ui.screens.vet.vetDetail.vetScreen
import com.example.veterinaria.ui.screens.vet.vetMap
import com.example.veterinaria.viewmodel.PetDetailViewModel
import com.example.veterinaria.viewmodel.PetListViewModel
import com.example.veterinaria.viewmodel.UserListViewModel
import com.example.veterinaria.viewmodel.VetDetailViewModel
import com.example.veterinaria.viewmodel.VetListViewModel

sealed class OpcionMenuInferior(val icono: ImageVector, val titulo: String, val ruta: String) {
    object HomeScreen : OpcionMenuInferior(Icons.Default.Home, "Home", "master")
    object MascotaScreen : OpcionMenuInferior(Icons.Default.Pets, "Mascota", "mascota")
    object MascotaDetalleScreen :
        OpcionMenuInferior(Icons.Default.Details, "Detalle", "mascota_detalle_screen")
}

sealed class OpcionMenuSuperior(val icono: ImageVector, val titulo: String, val ruta: String) {
    object MapaVeterinariaScreen :
        OpcionMenuSuperior(Icons.Default.Map, "Veterinaria Map", "mapa-veterinaria")

    object CitasScreen : OpcionMenuSuperior(Icons.Default.CalendarMonth, "Citas", "citas")
    object MisMascotasScreen :
        OpcionMenuSuperior(Icons.Default.Pets, "Mis Mascotas", "mis-mascotas")

    object CerrarSesion : OpcionMenuSuperior(Icons.Default.Close, "Cerrar Sesion", "cerrar-sesion")
    object Emergencia : OpcionMenuSuperior(Icons.Default.Warning, "Emergencia", "emergencia")
    object Veterinaria : OpcionMenuSuperior(Icons.Default.Details, "Unete", "add_vet")

    object Home : OpcionMenuSuperior(Icons.Default.Home, "Home", "home")
    object MisNotificaciones : OpcionMenuSuperior(Icons.Default.Alarm, "Mis Notificaciones", "notificaciones")
    object Telemedicina : OpcionMenuSuperior(Icons.Default.Phone, "Telemedicina", "telemedicina")
}

@OptIn(ExperimentalLayoutApi::class)
@ExperimentalMaterialApi
@Composable
fun NavegacionUsuario(
    navController: NavHostController,
    ubicacionLiveData: UbicacionLiveData,
    requestSinglePermissionLauncher: ActivityResultLauncher<String>
) {
    NavHost(navController = navController, startDestination = OpcionMenuInferior.HomeScreen.ruta) {

        composable(route = OpcionMenuSuperior.MapaVeterinariaScreen.ruta) {
            //InformacionVeterinariaScreen()
            val viewModel: VetListViewModel = hiltViewModel()
            val state = viewModel.state.value
            vetMap(state)
        }
        composable(route= OpcionMenuSuperior.Home.ruta){
            val viewModel: UserListViewModel = hiltViewModel()
            val state = viewModel.state.value

            val viewModelPets: PetListViewModel = hiltViewModel()
            val statePets = viewModelPets.state.value

            HomeScreen(state,statePets)
//            HomeScreen()
        }
        composable(route= OpcionMenuSuperior.MisNotificaciones.ruta){
            /*val viewModel: PetListViewModel = hiltViewModel()
            val state = viewModel.state.value
            PetListScreen(state)*/

            NavegationPet()
        }
        composable(route= OpcionMenuSuperior.MisMascotasScreen.ruta){
            /*val viewModel: PetDetailViewModel = hiltViewModel()
            val state = viewModel.state.value
            PetDetailScreen(
                state = state,
                addNewPet = viewModel::addNewPet,
            )*/

            NavegationPet()
        }

        composable(route = OpcionMenuSuperior.CitasScreen.ruta) {
            navegationDiary()
        }
        composable(route = OpcionMenuSuperior.Emergencia.ruta) {
            EmergenciaScreen(navController)
        }
        composable(route = PetRoutes.EmergenciaMapRoute.name + "/{emergenciaNombre}") { backStackEntry ->
            var emergenciaNombre: String? = backStackEntry.arguments?.getString("emergenciaNombre")
            EmergenciaMapScreen(ubicacionLiveData, requestSinglePermissionLauncher, emergenciaNombre)
        }
        composable(route = OpcionMenuSuperior.CerrarSesion.ruta) {
            //Todo Cerrar Sesion
        }
        composable(route = OpcionMenuSuperior.Veterinaria.ruta) {
            val viewModel: VetDetailViewModel = hiltViewModel()
            val state = viewModel.state.value
            vetScreen(
                state = state,
                addNewVet = viewModel::addNewVet,
                ubicacionLiveData
            )
        }

        //-------------------------Bottom Bar-----------------------------------------

        composable(route = OpcionMenuInferior.MascotaScreen.ruta) {
            /*val viewModel: VetListViewModel = hiltViewModel()
            val state = viewModel.state.value
            vetMap(state)*/
            /*val viewModel: PetListViewModel = hiltViewModel()
            val state = viewModel.state.value
            PetListScreen(state)*/
            NavegationPet()
        }
        composable(OpcionMenuInferior.HomeScreen.ruta) {

            val viewModel: UserListViewModel = hiltViewModel()
            val state = viewModel.state.value

            val viewModelPets: PetListViewModel = hiltViewModel()
            val statePets = viewModelPets.state.value

            HomeScreen(state,statePets)
//            HomeScreen(state)
//            HomeScreen()
        }

        composable(OpcionMenuInferior.MascotaDetalleScreen.ruta) {
            // ---------------------User---------------------
            val viewModel: UserListViewModel = hiltViewModel()
            val state = viewModel.state.value
            UserListScreen(state)
        }
    }
}

enum class PetRoutes {
    RegisterScreenRoute,
    ListPetScreenRoute,
    EmergenciaMapRoute
}