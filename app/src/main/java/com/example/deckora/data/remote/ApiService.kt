package com.example.deckora.data.remote

import retrofit2.http.Query
import com.example.deckora.data.model.LoginRequest
import com.example.deckora.data.model.LoginResponse
import com.example.deckora.data.model.api.CarpetaApi
import com.example.deckora.data.model.api.CartaApi
import com.example.deckora.data.model.api.ImgBBApi
import com.example.deckora.data.model.api.UsuarioApi
import com.example.deckora.data.model.api.UsuarioWrapper
import com.example.deckora.data.model.api.ResumenApi
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.PATCH
import retrofit2.http.Part

interface ApiService {

    // Usuarios
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

    //Carta
    @POST("/api/v2/cartas/usuario/{idUsuario}/carpeta/{idCarpeta}")
    suspend fun crearCarta(
        @Path("idUsuario") idUsuario: Long,
        @Path("idCarpeta") idCarpeta: Long,
        @Body carta: CartaApi
    ): CartaApi

    //Carpeta
    @POST("/api/v2/carpetas/usuario/{idUsuario}")
    suspend fun crearCarpeta(
        @Path("idUsuario") idUsuario: Long,
        @Body carpeta: CarpetaApi
    ): CarpetaApi


    //Categoria


    //Resumenes
    @GET("/api/v2/resumenes")
    suspend fun getResumenes(): List<ResumenApi>   // si desactivaste HAL

    @GET("/api/v2/resumenes/{id}")
    suspend fun getResumen(@Path("id") id: Long): ResumenApi

    @POST("/api/v2/resumenes")
    suspend fun createResumen(@Body resumen: ResumenApi): ResumenApi

    @PUT("/api/v2/resumenes/{id}")
    suspend fun updateResumen(
        @Path("id") id: Long,
        @Body resumen: ResumenApi
    ): ResumenApi

    @PATCH("/api/v2/resumenes/{id}/mover-carta")
    suspend fun moverCarta(
        @Path("id") id: Long,
        @Query("idCarpetaNueva") idCarpetaNueva: Long
    ): ResumenApi

    @DELETE("/api/v2/resumenes/{id}/usuario")
    suspend fun deleteUsuarioFromResumen(@Path("id") id: Long): Response<Unit>

    @DELETE("/api/v2/resumenes/{id}/carta")
    suspend fun deleteCartaFromResumen(@Path("id") id: Long): Response<Unit>

    @DELETE("/api/v2/resumenes/{id}/carpeta")
    suspend fun deleteCarpetaFromResumen(@Path("id") id: Long): Response<Unit>

    @GET("/api/v2/resumenes/usuario/{idUsuario}/carpetas")
    suspend fun getCarpetasByUsuario(
        @Path("idUsuario") idUsuario: Long
    ): List<CarpetaApi>

    @GET("/api/v2/resumenes/carpeta/{idCarpeta}/cartas")
    suspend fun getCartasByCarpeta(
        @Path("idCarpeta") idCarpeta: Long
    ): List<CartaApi>

    //Imagenes usando imgbb
    @Multipart
    @POST("https://api.imgbb.com/1/upload")
    suspend fun subirImagen(
        @Query("key") apiKey: String,
        @Part image: MultipartBody.Part
    ): ImgBBApi

}
