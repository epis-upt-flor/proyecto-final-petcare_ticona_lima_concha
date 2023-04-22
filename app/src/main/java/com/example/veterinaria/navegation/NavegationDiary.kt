    package com.example.veterinaria.navegation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.veterinaria.screens.diary.diary_detail.DiaryDetailScreen
import com.example.veterinaria.screens.diary.diary_detail.DiaryDetailViewModel
import com.example.veterinaria.screens.diary.diary_list.DiaryListScreen
import com.example.veterinaria.screens.diary.diary_list.DiaryListViewModel

@ExperimentalMaterialApi
@Composable
fun navegationDiary() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = DestinationDiary.DiaryList.route,
    ){
        addDiaryList(navController)

        addDiaryDetail()
    }
}
@ExperimentalMaterialApi
fun NavGraphBuilder.addDiaryList(
    navController: NavController
){
    composable(
        route = DestinationDiary.DiaryList.route
    ){

        val viewModel: DiaryListViewModel = hiltViewModel()
        val state = viewModel.state.value
        val isRefreshing = viewModel.isRefreshing.collectAsState()

        DiaryListScreen(
            state = state,
            navigateToDiaryDetail = {
                navController.navigate(DestinationDiary.DiaryDetail.route)
            },
            isRefreshing = isRefreshing.value,
            refreshData = viewModel::getDiaryList,
            onItemClick = {diaryId ->
                navController.navigate(
                    DestinationDiary.DiaryDetail.route + "?diaryId=$diaryId"
                )
            }

        )
    }
}
fun NavGraphBuilder.addDiaryDetail() {
    composable(
        route = DestinationDiary.DiaryDetail.route + "?diaryId={diaryId}"
    ){

        val viewModel: DiaryDetailViewModel = hiltViewModel()
        val state = viewModel.state.value

        DiaryDetailScreen(
            state = state,
            addNewDiary = viewModel::addNewDiary,
            updateDiary = viewModel::updateDiary
        )
    }
}