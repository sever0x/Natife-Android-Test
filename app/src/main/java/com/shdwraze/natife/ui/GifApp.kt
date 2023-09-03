@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.shdwraze.natife.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.shdwraze.natife.ui.screen.GifFullScreen
import com.shdwraze.natife.ui.screen.HomeScreen
import com.shdwraze.natife.ui.screen.MainAppBar

enum class GifScreen {
    List,
    Gif
}

@Composable
fun GifApp(
    navController: NavHostController = rememberNavController()
) {
    val gifViewModel: GifViewModel = viewModel(factory = GifViewModel.Factory)
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val searchWidgetState = gifViewModel.searchWidgetState
    val searchTextState = gifViewModel.searchTextState

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val topBarVisible = rememberSaveable { mutableStateOf(true) }

    when (navBackStackEntry?.destination?.route) {
        GifScreen.List.name -> topBarVisible.value = true
        "${GifScreen.Gif.name}/{gifId}" -> topBarVisible.value = false
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            AnimatedVisibility(visible = topBarVisible.value) {
                MainAppBar(
                    searchWidgetState = searchWidgetState.value,
                    searchTextState = searchTextState.value,
                    onTextChange = {
                        gifViewModel.updateSearchTextState(newValue = it)
                    },
                    onCloseClicked = {
                        gifViewModel.updateSearchWidgetState(newValue = SearchWidgetState.CLOSED)
                    },
                    onSearchClicked = {
                        gifViewModel.getSearchGifs(it)
                    },
                    onSearchTriggered = {
                        gifViewModel.updateSearchWidgetState(SearchWidgetState.OPENED)
                    },
                    scrollBehavior = scrollBehavior
                )
            }
        }
    ) {

        NavHost(
            navController = navController,
            startDestination = GifScreen.List.name,
            modifier = Modifier.padding(it)
        ) {
            composable(route = GifScreen.List.name) {
                HomeScreen(
                    gifUiState = gifViewModel.gifUiState,
                    retryAction = gifViewModel::getTrendingGifs,
                    navController = navController,
                    gifViewModel = gifViewModel
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