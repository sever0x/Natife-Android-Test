package com.shdwraze.natife.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.shdwraze.natife.data.Gif
import com.shdwraze.natife.util.getImageLoader

@Composable
fun GifFullScreen(
    gifId: String,
    gifs: List<Gif>,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val imageLoader = getImageLoader(context)
    val gif = gifs.find { it.id == gifId }

    Box(
        modifier = modifier.fillMaxSize()
    ) {

        Image(
            painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(context)
                    .data(gif?.imageLink)
                    .crossfade(false)
                    .build(),
                imageLoader = imageLoader
            ),
            contentDescription = gif?.title,
            modifier = Modifier.fillMaxSize()
        )
    }
}