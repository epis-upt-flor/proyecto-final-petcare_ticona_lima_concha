package com.example.veterinaria.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.veterinaria.R
import com.example.veterinaria.ui.components.BarraNavegacionInferior
import com.example.veterinaria.ui.components.DrawerContent
import com.example.veterinaria.ui.components.MenuNavegacionSuperior
import com.example.veterinaria.ui.navegation.NavegacionUsuario
import com.example.veterinaria.ui.navegation.OpcionMenuSuperior
import com.example.veterinaria.ui.navegation.OpcionMenuInferior
import com.example.veterinaria.ui.screens.emergencia.UbicacionLiveData
import com.example.veterinaria.ui.theme.VeterinariaTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
@ExperimentalMaterialApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var ubicacionLiveData: UbicacionLiveData
    private val requestSinglePermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { estaPermisoConcedido ->
        if(estaPermisoConcedido == true) {
            ubicacionLiveData.startLocationUpdates()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.ubicacionLiveData = UbicacionLiveData(baseContext)

        setContent {
            VeterinariaTheme {
                PrincipalScreen(ubicacionLiveData, requestSinglePermissionLauncher)
            }
        }
    }
}

@ExperimentalMaterialApi
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PrincipalScreen(
    ubicacionLiveData: UbicacionLiveData,
    requestSinglePermissionLauncher: ActivityResultLauncher<String>
) {
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    val navItemsList = listOf(OpcionMenuInferior.HomeScreen, OpcionMenuInferior.MascotaScreen, OpcionMenuInferior.MascotaDetalleScreen)
    val scope = rememberCoroutineScope()
    Scaffold(
        scaffoldState = scaffoldState,
        drawerGesturesEnabled = false,
        drawerContent = { DrawerContent(scope, scaffoldState, navController) },
        topBar = { MenuNavegacionSuperior(scope, scaffoldState) },
        bottomBar = { BarraNavegacionInferior( navController, navItemsList ) }
    ) {
        NavegacionUsuario(navController, ubicacionLiveData, requestSinglePermissionLauncher)
    }
}
