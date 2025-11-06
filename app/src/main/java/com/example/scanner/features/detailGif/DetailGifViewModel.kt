package com.example.scanner.features.detailGif

import androidx.lifecycle.ViewModel
import com.example.scanner.features.gifList.Gif
import com.example.scanner.features.gifList.sampleGif
import com.example.scanner.features.gifList.samplesGif
import kotlinx.coroutines.flow.MutableStateFlow

sealed class GifUiState{
    data object Loading : GifUiState()
    data class Success(val gif: Gif) : GifUiState()
    data class Failure(val message: String) : GifUiState()
}

class DetailGifViewModel: ViewModel() {
    val gifFlow = MutableStateFlow<Gif?>(null)
    val uiState = MutableStateFlow<GifUiState>(GifUiState.Loading)

    fun loadGif(gifId : String){

        uiState.value = GifUiState.Loading

        val gif = samplesGif.find{it.id == gifId}

        if (gif == null) {
            uiState.value = GifUiState.Failure("error")
        }
        else {
            uiState.value = GifUiState.Success(
                gif = gif
            )
        }
    }


}