package com.shdwraze.natife.ui

import android.content.pm.ApplicationInfo
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.google.gson.internal.GsonBuildConfig
import com.shdwraze.natife.BuildConfig
import com.shdwraze.natife.GifApplication
import com.shdwraze.natife.data.Gif
import com.shdwraze.natife.data.GifRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface GifUiState {
    data class Success(val gifs: List<Gif>) : GifUiState
    object Error: GifUiState
    object Loading: GifUiState
}

class GifViewModel(
    private val gifRepository: GifRepository
) : ViewModel() {

    var gifUiState: GifUiState by mutableStateOf(GifUiState.Loading)
        private set

    init {
        getTrendingGifs()
    }

    fun getTrendingGifs(apiKey: String = BuildConfig.apiKey, limit: Int = 25) {
        println(BuildConfig.apiKey)
        viewModelScope.launch {
            gifUiState = GifUiState.Loading
            gifUiState = try {
                GifUiState.Success(gifRepository.getTrendingGifs(apiKey, limit))
            } catch (e: IOException) {
                GifUiState.Error
            } catch (e: HttpException) {
                GifUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as GifApplication)
                val gifRepository = application.container.gifRepository
                GifViewModel(gifRepository = gifRepository)
            }
        }
    }
}