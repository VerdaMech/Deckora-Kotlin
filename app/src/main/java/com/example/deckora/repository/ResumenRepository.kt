package com.example.deckora.repository

import com.example.deckora.data.model.api.CarpetaApi
import com.example.deckora.data.model.api.CartaApi
import com.example.deckora.data.model.api.ResumenApi
import com.example.deckora.data.model.api.UsuarioApi
import com.example.deckora.data.remote.ApiClient
import retrofit2.Response


class ResumenRepository {

    private val api = ApiClient.service

    // Obtener todos los resúmenes
    suspend fun getResumenes(): List<ResumenApi> {
        return api.getResumenes()
    }

    // Obtener resumen por ID
    suspend fun getResumenById(idResumen: Long): ResumenApi {
        return api.getResumen(idResumen)
    }

    // Crear resumen
    suspend fun createResumen(resumen: ResumenApi): ResumenApi {
        return api.createResumen(resumen)
    }

    // Actualizar resumen
    suspend fun updateResumen(idResumen: Long, resumen: ResumenApi): ResumenApi {
        return api.updateResumen(idResumen, resumen)
    }

    // Mover carta de carpeta
    suspend fun moverCarta(idResumen: Long, idCarpetaNueva: Long): ResumenApi {
        return api.moverCarta(idResumen, idCarpetaNueva)
    }

    // Eliminar usuario asociado al resumen
    suspend fun borrarUsuario(idResumen: Long): Response<Unit> {
        return api.deleteUsuarioFromResumen(idResumen)
    }

    // Eliminar carta asociada al resumen
    suspend fun borrarCarta(idResumen: Long): Response<Unit> {
        return api.deleteCartaFromResumen(idResumen)
    }

    // Eliminar carpeta asociada al resumen
    suspend fun borrarCarpeta(idResumen: Long): Response<Unit> {
        return api.deleteCarpetaFromResumen(idResumen)
    }
    // Carpetas asociadas a un usuario
    suspend fun getCarpetasByUsuario(idUsuario: Long): List<CarpetaApi> {
        return api.getCarpetasByUsuario(idUsuario)
    }

    // Cartas dentro de una carpeta
    suspend fun getCartasByCarpeta(idCarpeta: Long): List<CartaApi> {
        return api.getCartasByCarpeta(idCarpeta)
    }
}
