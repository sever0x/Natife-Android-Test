package com.shdwraze.natife.ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.shdwraze.natife.R
import com.shdwraze.natife.data.Gif
import com.shdwraze.natife.ui.GifScreen
import com.shdwraze.natife.util.getImageLoader

@Composable
fun GifGridScreen(
    gifs: List<Gif>,
    navController: NavController
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(dimensionResource(id = R.dimen.grid_cell)),
        verticalItemSpacing = dimensionResource(id = R.dimen.spacing),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.spacing)),
        modifier = Modifier.fillMaxSize()
    ) {
        items(gifs) { gif ->
            GifCard(
                gif = gif,
                navController = navController
            )
        }
    }
}

@Composable
fun GifCard(
    gif: Gif,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val context = LocalContext.current
    val imageLoader = getImageLoader(context)
    Card(
        modifier = modifier.clickable {
            navController.navigate("${GifScreen.Gif.name}/${gif.id}")
        },
        shape = RectangleShape
    ) {
        AsyncImage(
            model =
            ImageRequest.Builder(context = context)
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
}