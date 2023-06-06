package com.example.veterinaria.screens.img

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.veterinaria.screens.img.repository.ImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageViewModel @Inject constructor(
    private val repo: ImageRepository
):ViewModel(){
    var addImageToStorangeResponse by mutableStateOf<Response<Uri>>(Response.Sucess(null))
    private set
    var addImageToDatabaseResponse by mutableStateOf<Response<Boolean>>(Response.Sucess(null))
    private set
    var getImageFromDatabaseResponse by mutableStateOf<Response<String>>(Response.Sucess(null))
        private set
    fun addImageToStorange(imageUri:Uri) = viewModelScope.launch {
        addImageToStorangeResponse = Response.Loading
        addImageToStorangeResponse = repo.addImageToFirebaseStorage(imageUri)
    }
    fun addImageToDatabase(downloadUrl:Uri) = viewModelScope.launch{
        addImageToDatabaseResponse = Response.Loading
        addImageToDatabaseResponse = repo.addImageUrlToFirestore(downloadUrl)
    }
    fun getImagenFromDatabase()=viewModelScope.launch{
        getImageFromDatabaseResponse = Response.Loading
        getImageFromDatabaseResponse = repo.addImageUrlFromFirestore()
    }
}