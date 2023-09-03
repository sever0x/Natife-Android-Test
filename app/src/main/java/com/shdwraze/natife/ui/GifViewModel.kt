package com.shdwraze.natife.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.shdwraze.natife.BuildConfig
import com.shdwraze.natife.GifApplication
import com.shdwraze.natife.data.Gif
import com.shdwraze.natife.data.GifRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface GifUiState {
    data class Success(val gifs: List<Gif>) : GifUiState
    object Error : GifUiState
    object Loading : GifUiState
}

class GifViewModel(
    private val gifRepository: GifRepository
) : ViewModel() {

    var gifUiState: GifUiState by mutableStateOf(GifUiState.Loading)
        private set

    private val _searchWidgetState: MutableState<SearchWidgetState> =
        mutableStateOf(value = SearchWidgetState.CLOSED)
    val searchWidgetState: State<SearchWidgetState> = _searchWidgetState

    private val _searchTextState: MutableState<String> =
        mutableStateOf(value = "")
    val searchTextState: State<String> = _searchTextState

    fun updateSearchWidgetState(newValue: SearchWidgetState) {
        _searchWidgetState.value = newValue
    }

    fun updateSearchTextState(newValue: String) {
        _searchTextState.value = newValue
    }

    init {
        getTrendingGifs()
    }

    fun getTrendingGifs(apiKey: String = BuildConfig.apiKey, limit: Int = 25) {
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

    fun getSearchGifs(query: String, apiKey: String = BuildConfig.apiKey, limit: Int = 25) {
        viewModelScope.launch {
            gifUiState = GifUiState.Loading
            gifUiState = try {
                GifUiState.Success(gifRepository.getSearchGifs(apiKey, query, limit))
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

enum class SearchWidgetState {
    OPENED, CLOSED
}