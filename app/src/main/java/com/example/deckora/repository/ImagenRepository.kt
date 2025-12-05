package com.example.deckora.repository

import com.example.deckora.data.remote.ImgBBClient
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class ImagenRepository {

    private val apiKey = "aff03dcf85d4fcc6a50e5b74ee5759f4"

    suspend fun subirImagenSuspend(file: File): String {

        val requestBody = file.asRequestBody("image/*".toMediaType())
        val filePart = MultipartBody.Part.createFormData("image", file.name, requestBody)

        val keyPart = apiKey.toRequestBody("text/plain".toMediaType())

        val response = ImgBBClient.api.uploadImage(
            image = filePart,
            apiKey = keyPart
        )

        return response.data.url
    }
}





