package com.example.deckora.repository

import com.example.deckora.data.model.api.CartaApi
import com.example.deckora.data.remote.ApiClient

class CartaRepository {

    private val api = ApiClient.service

    suspend fun crearCarta(
        idUsuario: Long,
        idCarpeta: Long,
        carta: CartaApi
    ): CartaApi {
        return api.crearCarta(idUsuario, idCarpeta, carta)
    }
}
