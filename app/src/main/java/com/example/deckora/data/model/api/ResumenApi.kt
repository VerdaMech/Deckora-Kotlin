package com.example.deckora.data.model.api

data class ResumenApi(
    val id: Long? = null,
    val usuario: UsuarioApi?,
    val carta: CartaApi?,
    val carpeta: CarpetaApi?
)
