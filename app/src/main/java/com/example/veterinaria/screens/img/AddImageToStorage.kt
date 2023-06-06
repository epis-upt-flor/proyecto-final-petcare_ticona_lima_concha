package com.example.veterinaria.screens.img

import android.net.Uri
import android.widget.ProgressBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun AddImageToStorage(
    viewModel: ImageViewModel = hiltViewModel(),
    addImageToDatabase : (downloadUrl: Uri) -> Unit
) {
    when ( val addImageToStorageResponse = viewModel.addImageToStorangeResponse){
        is Response.Loading -> ProgressBar()
        is Response.Sucess -> addImageToStorageResponse.data?.let { downloadUrl ->
            LaunchedEffect(downloadUrl) {
                addImageToDatabase(downloadUrl)
            }
        }
        is Response.Failure -> print(addImageToStorageResponse.e)
    }
}