package com.example.veterinaria.screens.img.repository

import android.net.Uri
import com.example.veterinaria.screens.img.Response

typealias AddImageToStorageResponse = Response<Uri>
typealias AddImageUrlToFirestoreResponse = Response<Boolean>
typealias GetImageFromFirestoreResponse = Response<String>

interface ImageRepository {
    suspend fun addImageToFirebaseStorage (imageUri: Uri): AddImageToStorageResponse
    suspend fun addImageUrlToFirestore (download: Uri): AddImageUrlToFirestoreResponse
    suspend fun addImageUrlFromFirestore (): GetImageFromFirestoreResponse
}