package com.example.veterinaria.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.veterinaria.data.model.Request
import com.example.veterinaria.data.model.RequestDetailState
import com.example.veterinaria.data.repository.RequestRepository
import com.google.firebase.firestore.FieldValue
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class RequestViewModel
@Inject
constructor(
    private val reqRepository: RequestRepository,
    savedStateHandle: SavedStateHandle, // almacenamiento del id
) : ViewModel() {
    private val _state: MutableState<RequestDetailState> = mutableStateOf(RequestDetailState())
    val state: State<RequestDetailState>
        get() = _state

    fun addRequest(user: String, vet: String): String {
        val req = Request(
            id = UUID.randomUUID().toString(),
            userid = user,
            vetid = vet,
            date = FieldValue.serverTimestamp()
        )
        // enviando informacion a firebase
        reqRepository.addRequest(req)

        return req.id
    }
}