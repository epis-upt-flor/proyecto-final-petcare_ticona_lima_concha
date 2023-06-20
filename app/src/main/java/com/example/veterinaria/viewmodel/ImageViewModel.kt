package com.example.veterinaria.viewmodel

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.veterinaria.data.repository.ImageRepository
import com.example.veterinaria.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

    private val _imageUrl = MutableStateFlow<String?>(null)
    val imageUrl: StateFlow<String?> = _imageUrl

    fun addImageToStorange(imageUri:Uri) = viewModelScope.launch {
        addImageToStorangeResponse = Response.Loading
        addImageToStorangeResponse = repo.addImageToFirebaseStorage(imageUri)
    }
    fun addImageToDatabase(downloadUrl:Uri) = viewModelScope.launch{
        addImageToDatabaseResponse = Response.Loading
        addImageToDatabaseResponse = repo.addImageUrlToFirestore(downloadUrl)

        _imageUrl.value = downloadUrl.toString()
    }
    fun getImagenFromDatabase()=viewModelScope.launch{
        getImageFromDatabaseResponse = Response.Loading
        getImageFromDatabaseResponse = repo.addImageUrlFromFirestore()
    }
}