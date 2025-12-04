package com.example.deckora.repository

import com.example.deckora.data.remote.ApiClient
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class ImagenRepository {

    private val api = ApiClient.service
    private val apiKey = "TU_API_KEY_AQUI"

    suspend fun subirImagen(file: File): String {
        val request = file.asRequestBody("image/*".toMediaType())
        val multipart = MultipartBody.Part.createFormData(
            "image",
            file.name,
            request
        )

        val response = api.subirImagen(apiKey, multipart)

        if (response.success) {
            return response.data.url
        } else {
            throw Exception("Error subiendo imagen")
        }
    }
}
