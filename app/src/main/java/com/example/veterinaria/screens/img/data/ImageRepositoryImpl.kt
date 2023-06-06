package com.example.veterinaria.screens.img.data

import android.net.Uri
import com.example.veterinaria.screens.img.Response
import com.example.veterinaria.screens.img.repository.AddImageToStorageResponse
import com.example.veterinaria.screens.img.repository.AddImageUrlToFirestoreResponse
import com.example.veterinaria.screens.img.repository.GetImageFromFirestoreResponse
import com.example.veterinaria.screens.img.repository.ImageRepository
import com.example.veterinaria.screens.img.util.Constants.CREATED_AT
import com.example.veterinaria.screens.img.util.Constants.IMAGES
import com.example.veterinaria.screens.img.util.Constants.UID
import com.example.veterinaria.screens.img.util.Constants.URL
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val storage: FirebaseStorage,
    private val db: FirebaseFirestore,
) : ImageRepository {
    override suspend fun addImageToFirebaseStorage(imageUri: Uri): AddImageToStorageResponse {
        return try {
            val uniqueId = UUID.randomUUID().toString()
            val imageName = "$uniqueId.jpg"
            //val downloadUrl = storage.reference.child(IMAGES).child(IMAGE_NAME)
            val downloadUrl = storage.reference.child(IMAGES).child(imageName)
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
            db.collection(IMAGES).document(UUID.randomUUID().toString()).set(mapOf(
                URL to download,
                CREATED_AT to FieldValue.serverTimestamp()
            )).await()
            Response.Sucess(true)
        }catch (e:Exception){
            Response.Failure(e)
        }
    }

    override suspend fun addImageUrlFromFirestore(): GetImageFromFirestoreResponse {
        return try {
            val imageUrl = db.collection(IMAGES).document(UID).get().await().getString(URL)
            Response.Sucess(imageUrl)
        }
        catch (e:Exception){
            Response.Failure(e)
        }
    }
}