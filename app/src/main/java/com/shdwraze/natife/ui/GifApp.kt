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
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.shdwraze.natife.ui.screen.GifFullScreen
import com.shdwraze.natife.ui.screen.HomeScreen

enum class GifScreen {
    List,
    Gif
}

@Composable
fun GifApp(
    navController: NavHostController = rememberNavController()
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            GifTopAppBar(scrollBehavior = scrollBehavior)
        }
    ) {
//        Surface(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(it)
//        ) {
//            val gifViewModel: GifViewModel = viewModel(factory = GifViewModel.Factory)
//            HomeScreen(
//                gifUiState = gifViewModel.gifUiState,
//                retryAction = gifViewModel::getTrendingGifs
//            )
//        }
        val gifViewModel: GifViewModel = viewModel(factory = GifViewModel.Factory)

        NavHost(
            navController = navController,
            startDestination = GifScreen.List.name,
            modifier = Modifier.padding(it)
        ) {
            composable(route = GifScreen.List.name) {
                HomeScreen(
                    gifUiState = gifViewModel.gifUiState,
                    retryAction = gifViewModel::getTrendingGifs,
                    navController = navController
                )
            }
            composable(
                route = "${GifScreen.Gif.name}/{gifId}",
                arguments = listOf(navArgument("gifId") {
                    type = NavType.StringType
                })
            ) { navBackStackEntry ->
                val gifId = navBackStackEntry.arguments?.getString("gifId")
                gifId?.let {
                    val gifUiState = gifViewModel.gifUiState

                    if (gifUiState is GifUiState.Success) {
                        val gifs = gifUiState.gifs

                        GifFullScreen(
                            gifId = gifId,
                            gifs = gifs
                        )
                    }
                }
            }
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