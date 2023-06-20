package com.example.veterinaria.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.veterinaria.data.model.User
import com.example.veterinaria.data.repository.UserRepository
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val auth: FirebaseAuth = Firebase.auth
    private val _loading = MutableLiveData(false)

    fun signInWithGoogleCredential(credential: AuthCredential, home: () -> Unit) =
        viewModelScope.launch {
            try {
                auth.signInWithCredential(credential)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            home()
                            Log.d("Credencial", "Logeando con google")
                        }
                    }
                    .addOnFailureListener {
                        Log.d("Credencial", "Error con google")
                    }

            } catch (ex: Exception) {
                Log.d("Credencial", "Exception ${ex.localizedMessage}")
            }
        }

    fun signInWithEmailAndPassword(email: String, password: String, home: () -> Unit)
    // ejecucion en segundo plano
            = viewModelScope.launch {
        try {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("Mascota", "Logueando: ")
                        home()
                    } else {
                        Log.d("Mascota", "Logueando: ${task.result.toString()}")
                    }
                }
        } catch (ex: Exception) {
            Log.d("Mascota", "Logueando: ${ex.message}")
        }
    }

    fun createUserWithEmailAndPassword(
        email: String,
        password: String,
        phone: String,
//        photo: String,
        home: () -> Unit,
    ) {
        if (_loading.value == false) {
            _loading.value == true
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        val displayName =
                            task.result.user?.email?.split("@")?.get(0)

                        createUser(displayName,phone,password)
//                        createUser(displayName,photo,password)
//                        addNewDiary(displayName, photo, password)
                        home()
                    } else {
                        Log.d(
                            "Mascota",
                            "createUserWithEmailAndPassword: ${task.result.toString()}"
                        )
                    }
                    _loading.value = false
                }
        }
    }

//    fun addNewDiary(displayName: String?, photo: String?, password: String) {
//        val userIds = auth.currentUser?.uid
//        val user = User(
//            userId = userIds.toString(),
//            username = displayName.toString(),
//            email = "${displayName.toString()}@gmail.com",
//            phone = "952525911",
//            password = password,
//            photo = "https://firebasestorage.googleapis.com/v0/b/mascota002-3b966.appspot.com/o/images%2Fuser%2Fuser.png?alt=media&token=c417fd7a-d01e-4777-b095-47572c1a3c6b",
//        )
//        //UserRepository.addNewUser(user)
//    }

    private fun createUser(displayName: String?, phone: String?, password: String) {
        val userIds = auth.currentUser?.uid
        val user = User(
            userId = userIds.toString(),
            username = displayName.toString(),
            email = "${displayName.toString()}@gmail.com",
            phone = "+51 $phone",
            password = password,
            photo = "https://firebasestorage.googleapis.com/v0/b/mascota002-3b966.appspot.com/o/images%2Fuser%2Fuser.png?alt=media&token=c417fd7a-d01e-4777-b095-47572c1a3c6b",

            ).toMap()

        FirebaseFirestore.getInstance().collection("users")
            .add(user)
            .addOnSuccessListener {
                Log.d("ClaroPs", "Creado: ${it.id}")
            }.addOnFailureListener {
                Log.d("ClaroPs", "Error: $it")
            }

    }
}