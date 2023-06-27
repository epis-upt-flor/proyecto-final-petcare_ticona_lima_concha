package com.example.veterinaria.ui.navegation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.veterinaria.ui.screens.vet.VeterinaryDetailScreen
import com.example.veterinaria.ui.screens.vet.VeterinaryListScreen
import com.example.veterinaria.viewmodel.VetDetailViewModel
import com.example.veterinaria.viewmodel.VetListViewModel

@ExperimentalMaterialApi
@Composable
fun NavigationVeterinary() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = DestinationVeterinary.VeterinaryList.route,
    ) {
        addVeterinaryList(navController)
        addVeterinaryDetail()
    }
}

@ExperimentalMaterialApi
fun NavGraphBuilder.addVeterinaryList(navController: NavController) {
    composable(route = DestinationVeterinary.VeterinaryList.route) {
        val viewModel: VetListViewModel = hiltViewModel()
        val state = viewModel.state.value
        val isRefreshing = viewModel.isRefreshing.collectAsState()

        VeterinaryListScreen(
            state = state,
            isRefreshing = isRefreshing.value,
            refreshData = viewModel::getListVet
        ) { veterinaryId ->
            navController.navigate(
                DestinationVeterinary.VeterinaryDetail.route + "?veterinaryId=$veterinaryId"
            )
        }
    }
}

fun NavGraphBuilder.addVeterinaryDetail() {
    composable(route = DestinationVeterinary.VeterinaryDetail.route + "?veterinaryId={veterinaryId}") {
        val viewModel: VetDetailViewModel = hiltViewModel()
        val state = viewModel.state.value

        VeterinaryDetailScreen(
            state = state,
            updateVeterinary = viewModel::updateVeterinary
        )
    }
}
