@file:OptIn(ExperimentalMaterial3Api::class)

package com.shdwraze.natife.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.viewmodel.compose.viewModel
import com.shdwraze.natife.ui.screen.HomeScreen

@Composable
fun GifApp() {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            GifTopAppBar(scrollBehavior = scrollBehavior)
        }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            val gifViewModel: GifViewModel = viewModel(factory = GifViewModel.Factory)
            HomeScreen(
                gifUiState = gifViewModel.gifUiState,
                retryAction = gifViewModel::getTrendingGifs
            )
        }
    }
}

@Composable
fun GifTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior
) {
    CenterAlignedTopAppBar(scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = "Giphy",
                style = MaterialTheme.typography.headlineSmall
            )
        })
}