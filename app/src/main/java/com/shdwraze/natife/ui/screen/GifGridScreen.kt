package com.shdwraze.natife.ui.screen

import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.shdwraze.natife.R
import com.shdwraze.natife.data.Gif

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GifGridScreen(gifs: List<Gif>) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(dimensionResource(id = R.dimen.grid_cell)),
        verticalItemSpacing = dimensionResource(id = R.dimen.spacing),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.spacing)),
        modifier = Modifier.fillMaxSize()
    ) {
        items(gifs) { gif ->
            GifCard(gif = gif)
        }
    }
}

@Composable
fun GifCard(
    gif: Gif,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()

    AsyncImage(
        model =
        ImageRequest.Builder(context = LocalContext.current)
            .data(gif.previewLink)
            .crossfade(true)
            .build(),
        imageLoader = imageLoader,
        contentDescription = stringResource(R.string.gif),
        modifier = modifier.fillMaxWidth(),
        contentScale = ContentScale.Crop,
        error = painterResource(R.drawable.ic_broken_image),
        placeholder = painterResource(R.drawable.loading_img)
    )
}