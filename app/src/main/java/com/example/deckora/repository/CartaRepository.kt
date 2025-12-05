package com.example.deckora.repository

import com.example.deckora.data.model.api.CartaApi
import com.example.deckora.data.model.api.CategoriaApi
import com.example.deckora.data.remote.ApiClient

class CartaRepository {

    private val api = ApiClient.service

    suspend fun crearCarta(
        idUsuario: Long,
        idCarpeta: Long,
        nombre: String,
        estado: String,
        descripcion: String,
        imagenUrl: String,
        categoriaId: Long
    ): CartaApi {

        val carta = CartaApi(
            id = null,
            nombre_carta = nombre,
            estado = estado,
            descripcion = descripcion,
            imagen_url = imagenUrl,
            categoria = CategoriaApi(categoriaId, "Magic")
        )

        return api.crearCarta(
            idUsuario = idUsuario,
            idCarpeta = idCarpeta,
            carta = carta
        )
    }
}
