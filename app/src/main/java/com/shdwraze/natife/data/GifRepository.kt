package com.shdwraze.natife.data

import com.shdwraze.natife.network.GifService

interface GifRepository {
    suspend fun getTrendingGifs(apiKey: String, limit: Int): List<Gif>

    suspend fun getSearchGifs(apiKey: String, query: String, limit: Int): List<Gif>
}

class NetworkGifRepository(
    private val gifService: GifService
) : GifRepository {

    override suspend fun getTrendingGifs(apiKey: String, limit: Int): List<Gif> =
        gifService.getTrendingGifs(apiKey, limit).data.map { data ->
            Gif(
                id = data.id,
                title = data.title,
                previewLink = data.images?.downsizedMedium?.url,
                imageLink = data.images?.original?.url
            )
        }

    override suspend fun getSearchGifs(apiKey: String, query: String, limit: Int): List<Gif> =
        gifService.getSearchGifs(apiKey, query, limit).data.map { data ->
            Gif(
                id = data.id,
                title = data.title,
                previewLink = data.images?.downsizedMedium?.url,
                imageLink = data.images?.original?.url
            )
        }

}