package com.example.scanner.features.gifList

import androidx.lifecycle.ViewModel
import com.example.scanner.features.api.ApiUtils
import com.example.scanner.features.api.Gif
import com.example.scanner.features.api.samplesGif
import kotlinx.coroutines.flow.MutableStateFlow

sealed class GifListUiState {
    data object Loading : GifListUiState()
    data class Success(val gifs: List<Gif>) : GifListUiState()
    data class Failure(val message: String) : GifListUiState()
}

class GifListViewModel: ViewModel() {
    val gifFlow = MutableStateFlow<List<Gif>>(emptyList())
    val uiState = MutableStateFlow<GifListUiState>(GifListUiState.Loading)

    fun loadGif(){

        //call api
        val gifs = samplesGif
        //

        gifFlow.value = gifs
        uiState.value = GifListUiState.Success(gifs=gifs)
    }

    fun getGifByImage(base64Image : String) {
       // if (api.)
        val apiUtils = ApiUtils()

        apiUtils.searchVision(base64Image)
    }
}