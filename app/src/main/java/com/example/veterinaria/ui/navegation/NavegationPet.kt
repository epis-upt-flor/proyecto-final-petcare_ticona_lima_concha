package com.example.veterinaria.ui.navegation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.veterinaria.ui.screens.diary.diary_list.DiaryListScreen
import com.example.veterinaria.ui.screens.pet.petDetail.PetDetailScreen
import com.example.veterinaria.ui.screens.pet.petList.PetListScreen
import com.example.veterinaria.viewmodel.PetDetailViewModel
import com.example.veterinaria.viewmodel.PetListViewModel

@Composable
fun NavegationPet() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = DestinationPet.PetList.route,
    ) {
        addPetList(navController)

        addPeDetail()
    }

}

fun NavGraphBuilder.addPetList(
    navController: NavHostController,
) {
    composable(
        route = DestinationPet.PetList.route
    ) {
        val viewModel: PetListViewModel = hiltViewModel()
        val state = viewModel.state.value
        val isRefreshing = viewModel.isRefreshing.collectAsState()

        PetListScreen(
            state = state,
            navigateToPetDetail = {
                navController.navigate(DestinationPet.PetDetail.route)
            },
            isRefreshing = isRefreshing.value,
            refreshData = viewModel::getPetList,
            onItemClick = { petId->
                navController.navigate(
                    DestinationPet.PetDetail.route + "?petId=$petId"
                )
            }
        )



    }
}

fun NavGraphBuilder.addPeDetail() {
    composable(
        route = DestinationPet.PetDetail.route + "?petId={petId}"
    ) {
        val viewModel: PetDetailViewModel = hiltViewModel()
        val state = viewModel.state.value

        PetDetailScreen(
            state = state,
            addNewPet = viewModel::addNewPet,
            updatePets = viewModel::updatePets
        )
    }
}


