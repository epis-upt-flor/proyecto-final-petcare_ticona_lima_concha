package com.example.veterinaria.screens.login

import android.provider.ContactsContract.CommonDataKinds.Email
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class LoginScreenViewModel : ViewModel() {

    private val auth: FirebaseAuth = Firebase.auth
    private val _loading = MutableLiveData(false)

    fun signInWithEmailAndPassword(email: String,password:String, home:()->Unit)
    // ejecucion en segundo plano
    = viewModelScope.launch {
        try {
            auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener{task->
                    if (task.isSuccessful){
                        Log.d("Mascota","Logueando: ")
                        home()
                    }else{
                        Log.d("Mascota","Logueando: ${task.result.toString()}")
                    }
                }
        }catch (ex:Exception){
            Log.d("Mascota","Logueando: ${ex.message}")
        }
    }
    fun createUserWithEmailAndPassword(
        email: String,
        password: String,
        home:() -> Unit
    ){
        if (_loading.value == false){
            _loading.value == true
            auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener{task->
                    if (task.isSuccessful){
                        home()
                    }else{
                        Log.d("Mascota","createUserWithEmailAndPassword: ${task.result.toString()}")
                    }
                    _loading.value = false
                }
        }

    }



}