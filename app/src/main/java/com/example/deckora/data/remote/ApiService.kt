package com.example.deckora.data.remote

import com.example.deckora.data.model.LoginRequest
import com.example.deckora.data.model.LoginResponse
import com.example.deckora.data.model.UsuarioApi
import com.example.deckora.data.model.UsuarioWrapper
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.PATCH

interface ApiService {


    @GET("/api/v2/usuarios")
    suspend fun getUsers(): UsuarioWrapper

    @GET("/api/v2/usuarios/{id}")
    suspend fun getUsuarioById(@Path("id") id: Int): UsuarioApi

    @POST("/api/v2/usuarios")
    suspend fun createUser(@Body user: UsuarioApi): UsuarioApi

    @PUT("/api/v2/usuarios/{id}")
    suspend fun updateUser(@Path("id") id: Int, @Body user: UsuarioApi): UsuarioApi

    @PATCH("/api/v2/usuarios/{id}")
    suspend fun patchUsuario(@Path("id") id: Int, @Body fields: Map<String, Any>): UsuarioApi

    @DELETE("/api/v2/usuarios/{id}")
    suspend fun deleteUser(@Path("id") id: Int): Response<Unit>

    @POST("/api/v2/usuarios/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

}