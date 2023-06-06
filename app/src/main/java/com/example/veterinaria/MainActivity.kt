package com.example.veterinaria

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.rounded.Settings
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
import com.example.veterinaria.navegation.NavegacionUsuario
import com.example.veterinaria.navegation.OpcionMenuSuperior
import com.example.veterinaria.navegation.OpcionMenuInferior
import com.example.veterinaria.screens.emergencia.UbicacionLiveData
import com.example.veterinaria.ui.theme.Purple500
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
        bottomBar = { BarraNavegacionInferior( navController, navItemsList )}
    ) {
        NavegacionUsuario(navController, ubicacionLiveData, requestSinglePermissionLauncher)
    }
}

@Composable
fun MenuNavegacionSuperior(scope: CoroutineScope, scaffoldState: ScaffoldState) {
    TopAppBar(
        title = { Text("Clinica Veterinaria") },
        navigationIcon = {
            IconButton(onClick = { scope.launch { scaffoldState.drawerState.open() } }) {
                Icon(Icons.Filled.Menu, contentDescription = null)
            }
        },
        actions = {
            IconButton(onClick = { /* doSomething() */ }) {
                Icon(Icons.Filled.Favorite, contentDescription = "Localized description")
            }
        }
    )
}

@Composable
fun BarraNavegacionInferior(navController: NavHostController, navItemsList: List<OpcionMenuInferior>) {
    BottomAppBar() {
        BottomNavigation() {
            val rutaActual = ObtenerRutaActual(navController)
            navItemsList.forEach { item ->
                BottomNavigationItem(
                    selected = rutaActual == item.ruta,
                    onClick = { navController.navigate(item.ruta) },
                    icon = { Icon(imageVector = item.icono, contentDescription = item.titulo)},
                    label = { Text(text = item.titulo)}
                )
            }
        }
    }
}

@Composable
fun ObtenerRutaActual(navController: NavHostController): String? {
    val input by navController.currentBackStackEntryAsState()
    return input?.destination?.route
}

@Composable
fun DrawerContent(scope: CoroutineScope, scaffoldState: ScaffoldState, navController: NavHostController){
    Column(
        Modifier
            .fillMaxWidth(),
    ) {
        InformacionUsuarioCard("Pedro", "pedro@gmail.com", "https://xsgames.co/randomusers/assets/images/favicon.png")
        OpcionSuperior(scope, scaffoldState, navController, OpcionMenuSuperior.Home)
        OpcionSuperior(scope, scaffoldState, navController, OpcionMenuSuperior.MisNotificaciones)
        OpcionSuperior(scope, scaffoldState, navController, OpcionMenuSuperior.CitasScreen)
        OpcionSuperior(scope, scaffoldState, navController, OpcionMenuSuperior.MisMascotasScreen)
        OpcionSuperior(scope, scaffoldState, navController, OpcionMenuSuperior.Emergencia)
        OpcionSuperior(scope, scaffoldState, navController, OpcionMenuSuperior.Veterinaria)
        OpcionSuperior(scope, scaffoldState, navController, OpcionMenuSuperior.MapaVeterinariaScreen)
        OpcionSuperior(scope, scaffoldState, navController, OpcionMenuSuperior.CerrarSesion)
        ZonaRedesSociales()
    }
}

@Composable
fun ZonaRedesSociales() {
    Row(modifier = Modifier.padding(8.dp) ,verticalAlignment = Alignment.CenterVertically) {
        Image(
            modifier = Modifier.size(50.dp),
            painter = painterResource(id = R.mipmap.ic_facebook_foreground),
            contentDescription = "facebook icon"
        )
        Image(
            modifier = Modifier.size(40.dp),
            painter = painterResource(id = R.mipmap.ic_whatsapp_foreground),
            contentDescription = "whatsapp icon"
        )
    }
}

@Composable
fun OpcionSuperior(coroutineScope: CoroutineScope, scaffoldState: ScaffoldState, navController: NavHostController, opcionMenuSuperior: OpcionMenuSuperior) {
    val selected = opcionMenuSuperior.ruta == ObtenerRutaActual(navController)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .drawBehind {
                val strokeWidth = 1f
                val x = size.width - strokeWidth
                val y = size.height - strokeWidth

                //Linea divisoria inferior
                drawLine(
                    color = Color.Cyan,
                    start = Offset(0f, y),
                    end = Offset(x, y),
                    strokeWidth = strokeWidth
                )
            }
            .clickable {
                coroutineScope.launch {
                    scaffoldState.drawerState.close()
                }
                navController.navigate(opcionMenuSuperior.ruta)
            },
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
        ) {
            Icon(
                imageVector = opcionMenuSuperior.icono,
                contentDescription = "${opcionMenuSuperior.titulo} Icon"
            )
            Text(
                modifier = Modifier
                    .padding(start = 24.dp),
                text = opcionMenuSuperior.titulo,
            )
        }
    }
}

@Composable
fun InformacionUsuarioCard(name: String, email: String, photoUrl: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        AsyncImage(model = photoUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(8.dp)
                .size(64.dp)
                .clip(CircleShape)
                .border(2.dp, Color.Gray, CircleShape)
        )
        Column {
            Text(text = name)
            Text(text = email)
        }
    }
}