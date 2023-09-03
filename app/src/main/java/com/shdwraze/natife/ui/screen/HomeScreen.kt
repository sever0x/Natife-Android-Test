package com.shdwraze.natife.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.shdwraze.natife.ui.GifUiState
import com.shdwraze.natife.ui.GifViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    gifUiState: GifUiState,
    modifier: Modifier = Modifier,
    retryAction: () -> Unit,
    navController: NavController,
    gifViewModel: GifViewModel
) {
    val isRefreshing by gifViewModel.isRefreshing.collectAsStateWithLifecycle()

    val refreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = gifViewModel::refresh
    )

    Box(modifier = modifier.fillMaxSize().pullRefresh(refreshState)) {
        when (gifUiState) {
            is GifUiState.Success -> GifGridScreen(gifs = gifUiState.gifs, navController)
            is GifUiState.Loading -> LoadingScreen()
            is GifUiState.Error -> ErrorScreen(retryAction = retryAction)
        }

        PullRefreshIndicator(isRefreshing, refreshState, Modifier.align(Alignment.TopCenter))
    }
}