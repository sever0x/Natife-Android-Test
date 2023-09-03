package com.shdwraze.natife.network

import com.shdwraze.natife.network.model.Giphy
import retrofit2.http.GET
import retrofit2.http.Query

interface GifService {

    @GET("trending")
    suspend fun getTrendingGifs(
        @Query("api_key") apiKey: String,
        @Query("limit") limit: Int
    ) : Giphy

    @GET("search")
    suspend fun getSearchGifs(
        @Query("api_key") apiKey: String,
        @Query("q") query: String,
        @Query("limit") limit: Int,
    ) : Giphy
}