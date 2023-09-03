package com.shdwraze.natife.data

import com.shdwraze.natife.network.GifService

interface GifRepository {
    suspend fun getTrendingGifs(apiKey: String, limit: Int): List<Gif>
}

class NetworkGifRepository(
    private val gifService: GifService
) : GifRepository {

    override suspend fun getTrendingGifs(apiKey: String, limit: Int): List<Gif> =
        gifService.getTrendingGifs(apiKey, limit).data.map { data ->
            Gif(
                title = data.title,
                previewLink = data.images?.original?.webp,
                imageLink = data.images?.original?.url
            )
        }

}