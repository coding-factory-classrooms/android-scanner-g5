package com.example.scanner.features.gifList

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.scanner.features.api.ApiUtils
import com.example.scanner.features.api.Gif
import com.example.scanner.features.api.GifList
import io.paperdb.Paper
import kotlinx.coroutines.flow.MutableStateFlow

sealed class GifListUiState {
    data object Loading : GifListUiState()
    data class Success(val gifs: MutableList<Gif>?) : GifListUiState()
    data class Failure(val message: String) : GifListUiState()
}

class GifListViewModel: ViewModel() {
    val gifFlow = MutableStateFlow<MutableList<Gif>>(mutableListOf())
    val uiState = MutableStateFlow<GifListUiState>(GifListUiState.Loading)

    fun loadGif(){

        val gifsRaw = Paper.book().read<Any>("gifs")
        val gifs: MutableList<Gif> = when (gifsRaw) {
            is MutableList<*> -> gifsRaw.filterIsInstance<Gif>().toMutableList()
            is GifList -> gifsRaw.data.toMutableList()
            else -> mutableListOf()
        }

        Log.i("package:mine", "loadGif: ${gifs} gifs loaded")

        gifFlow.value = gifs
        uiState.value = GifListUiState.Success(gifs = gifs)
    }

    fun getGifByImage(base64Image : String) {

        uiState.value = GifListUiState.Loading

        val apiUtils = ApiUtils()

        apiUtils.searchVision(base64Image) { success, updatedList ->
            if (success) {
                val gifs = updatedList ?: run {
                    val readRaw = Paper.book().read<Any>("gifs")
                    when (readRaw) {
                        is MutableList<*> -> readRaw.filterIsInstance<Gif>().toMutableList()
                        is GifList -> readRaw.data.toMutableList()
                        else -> mutableListOf()
                    }
                }
                gifFlow.value = gifs
                uiState.value = GifListUiState.Success(gifs = gifs)
            } else {
                uiState.value = GifListUiState.Failure("API request failed")
            }
        }
    }


}