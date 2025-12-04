package com.example.deckora.repository

import com.example.deckora.data.model.LoginRequest
import com.example.deckora.data.model.LoginResponse
import com.example.deckora.data.model.api.UsuarioApi
import com.example.deckora.data.remote.ApiClient
import retrofit2.Response



class UserRepository {

    private val api = ApiClient.service

    // Obtener todos los usuarios
    // Login
    suspend fun loginUsuario(nombre: String, contrasenia: String): LoginResponse {
        val request = LoginRequest(nombre, contrasenia)
        return api.login(request)
    }

    // Obtener todos los usuarios
    suspend fun getUsuarios(): List<UsuarioApi> {
        return api.getUsers()._embedded.usuarioList
    }

    // Obtener usuario por ID
    suspend fun getUsuarioById(id: Int): UsuarioApi {
        return api.getUsuarioById(id)
    }

    // Crear usuario
    suspend fun createUsuario(usuario: UsuarioApi): UsuarioApi {
        return api.createUser(usuario)
    }

    // Actualizar usuario completo
    suspend fun updateUsuario(id: Int, usuario: UsuarioApi): UsuarioApi {
        return api.updateUser(id, usuario)
    }

    // Actualizar solo un campo (PATCH)
    suspend fun patchUsuario(id: Int, campo: Map<String, Any>): UsuarioApi {
        return api.patchUsuario(id, campo)
    }

    // Eliminar usuario
    suspend fun deleteUsuario(id: Int): Response<Unit> {
        return api.deleteUser(id)
    }
}
