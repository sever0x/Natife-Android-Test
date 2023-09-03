package com.shdwraze.natife.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.shdwraze.natife.ui.GifUiState

@Composable
fun HomeScreen(
    gifUiState: GifUiState,
    modifier: Modifier = Modifier,
    retryAction: () -> Unit
) {
    when (gifUiState) {
        is GifUiState.Success -> GifGridScreen(gifs = gifUiState.gifs)
        is GifUiState.Loading -> LoadingScreen()
        is GifUiState.Error -> ErrorScreen(retryAction = retryAction)
    }
}