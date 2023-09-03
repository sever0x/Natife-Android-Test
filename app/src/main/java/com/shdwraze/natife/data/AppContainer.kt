package com.shdwraze.natife.data

import com.shdwraze.natife.network.GifService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val gifRepository: GifRepository
}

class DefaultAppContainer : AppContainer {

    private val baseUrl = "https://api.giphy.com/v1/gifs/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: GifService by lazy {
        retrofit.create(GifService::class.java)
    }

    override val gifRepository: GifRepository by lazy {
        NetworkGifRepository(retrofitService)
    }
}