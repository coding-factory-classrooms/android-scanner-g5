package com.example.scanner.features.detailGif

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.scanner.features.api.Gif
import io.paperdb.Paper
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

        val gifs = Paper.book().read<MutableList<Gif>>("gifs") ?: mutableListOf()

        Log.i("package:mine", "loadGif: looking for gif with id $gifId")
        val gif = gifs.find{it.id == gifId}

        Log.i("package:mine", "loadGif: found gif: $gif")

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