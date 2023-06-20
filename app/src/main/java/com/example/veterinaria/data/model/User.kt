package com.example.veterinaria.data.model

data class User(
    var userId: String = "",
    var username: String = "",
    var email: String = "",
    var phone: String = "",
    var password: String = "",
    var photo: String = ""
){
    fun toMap(): MutableMap<String,Any>{
        return mutableMapOf(
            "userId" to this.userId,
            "username" to this.username,
            "email" to this.email,
            "phone" to this.phone,
            "password" to this.password,
            "photo" to this.photo
        )
    }
}