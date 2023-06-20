package com.example.veterinaria.data.repository

import android.net.Uri
import com.example.veterinaria.util.Constants
import com.example.veterinaria.util.Response
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

typealias AddImageToStorageResponse = Response<Uri>
typealias AddImageUrlToFirestoreResponse = Response<Boolean>
typealias GetImageFromFirestoreResponse = Response<String>

interface ImageRepository {
    suspend fun addImageToFirebaseStorage (imageUri: Uri): AddImageToStorageResponse
    suspend fun addImageUrlToFirestore (download: Uri): AddImageUrlToFirestoreResponse
    suspend fun addImageUrlFromFirestore (): GetImageFromFirestoreResponse
}

class ImageRepositoryImpl @Inject constructor(
    private val storage: FirebaseStorage,
    private val db: FirebaseFirestore,
) : ImageRepository {
    override suspend fun addImageToFirebaseStorage(imageUri: Uri): AddImageToStorageResponse {
        return try {
            val uniqueId = UUID.randomUUID().toString()
            val imageName = "$uniqueId.jpg"
            //val downloadUrl = storage.reference.child(IMAGES).child(IMAGE_NAME)
            val downloadUrl = storage.reference.child(Constants.IMAGES).child(imageName)
                .putFile(imageUri).await()
                .storage.downloadUrl.await()
            Response.Sucess(downloadUrl)

        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun addImageUrlToFirestore(download: Uri): AddImageUrlToFirestoreResponse {
        return try{
            //db.collection(IMAGES).document(UID).set(mapOf(
            db.collection(Constants.IMAGES).document(UUID.randomUUID().toString()).set(mapOf(
                Constants.URL to download,
                Constants.CREATED_AT to FieldValue.serverTimestamp()
            )).await()
            Response.Sucess(true)
        }catch (e:Exception){
            Response.Failure(e)
        }
    }

    override suspend fun addImageUrlFromFirestore(): GetImageFromFirestoreResponse {
        return try {
            val imageUrl = db.collection(Constants.IMAGES).document(Constants.UID).get().await().getString(
                Constants.URL
            )
            Response.Sucess(imageUrl)
        }
        catch (e:Exception){
            Response.Failure(e)
        }
    }
}