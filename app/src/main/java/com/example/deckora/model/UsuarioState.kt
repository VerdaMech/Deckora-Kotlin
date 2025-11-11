package com.example.deckora.model

data class UsuarioState (
    val nombre : String ="",
    val correo : String = "",
    val clave : String = "",
    val errores : UsuarioErrores = UsuarioErrores()
)
