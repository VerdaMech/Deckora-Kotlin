package com.example.deckora.data.model

data class LoginResponse(
    val id :Long,
    val nombre_usuario: String,
    val contrasenia_usuario: String,
    val correo_usuario : String
)