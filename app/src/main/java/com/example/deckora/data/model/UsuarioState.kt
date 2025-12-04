package com.example.deckora.data.model

data class UsuarioState (
    val nombre: String = "",
    val correo: String = "",
    val clave: String = "",
    val repiteClave: String = "",
    val errores: RegistroErrores = RegistroErrores(),
    val loginErrores: LoginErrores = LoginErrores(),
    val mostrarErrores: Boolean = false,
    val estadoLogin: Boolean = false
)