package com.example.deckora.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ImgBBClient {
    private const val BASE_URL = "https://api.imgbb.com/1/"

    val api: ImgBBApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ImgBBApi::class.java)
    }
}

