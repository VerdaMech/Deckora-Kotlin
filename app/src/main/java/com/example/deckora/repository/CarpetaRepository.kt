package com.example.deckora.repository

import com.example.deckora.data.model.api.CarpetaApi
import com.example.deckora.data.model.api.CartaApi
import com.example.deckora.data.model.api.ResumenApi
import com.example.deckora.data.model.api.UsuarioApi
import com.example.deckora.data.remote.ApiClient
import retrofit2.Response


class CarpetaRepository {
    private val api = ApiClient.service

    suspend fun crearCarpeta(idUsuario: Long, nombre: String): CarpetaApi {
        val carpeta = CarpetaApi(nombre_carpeta = nombre)
        return api.crearCarpeta(idUsuario, carpeta)
    }

}

