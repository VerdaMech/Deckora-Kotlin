package com.example.deckora.data.model.api

data class CartaApi(
    val id: Long? = null,
    val nombre_carta: String?,
    val estado: String?,
    val descripcion: String?,
    val imagen_url: String?,
    val categoria: CategoriaApi?
)
