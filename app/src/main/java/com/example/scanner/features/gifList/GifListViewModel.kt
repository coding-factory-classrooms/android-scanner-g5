package com.example.scanner.features.gifList

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.scanner.features.api.ApiUtils
import com.example.scanner.features.api.Feature
import com.example.scanner.features.api.Gif
import com.example.scanner.features.api.GifList
import com.example.scanner.features.api.GiphyApi
import com.example.scanner.features.api.ImageData
import com.example.scanner.features.api.RequestItem
import com.example.scanner.features.api.VisionApi
import com.example.scanner.features.api.VisionRequest
import com.example.scanner.features.api.VisionResponse
import io.paperdb.Paper
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.io.encoding.Base64

sealed class GifListUiState {
    data object Loading : GifListUiState()
    data class Success(val gifs: MutableList<Gif>?) : GifListUiState()
    data class Failure(val message: String) : GifListUiState()
}

class GifListViewModel: ViewModel() {
    val gifFlow = MutableStateFlow<MutableList<Gif>>(mutableListOf())
    val uiState = MutableStateFlow<GifListUiState>(GifListUiState.Loading)
    lateinit var apiGiphy: GiphyApi
    lateinit var apiVision: VisionApi

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

    fun testVision(base64: String){

        val request = VisionRequest(
            requests = listOf(
                RequestItem(
                    image = ImageData(content = base64),
                    features = listOf(Feature(type = "LABEL_DETECTION"))
                )
            )
        )


        val call = apiVision.postVision(
            apiKey = "AIzaSyD85K2Wzn3C4cB2xCrCXOUMHE7Q0dIxEAM",
            request = request
        )

        call.enqueue(object : Callback<VisionResponse> {

            override fun onResponse(
                call: Call<VisionResponse?>,
                response: Response<VisionResponse?>
            ) {
                val visionResponse = response.body()!!
                Log.i("package:mine", "onResponse: $visionResponse")
                uiState.value = GifListUiState.Success(null)
            }

            override fun onFailure(
                call: Call<VisionResponse?>,
                t: Throwable
            ) {
                Log.e("package:mine", "onFailure: ", t)
                uiState.value = GifListUiState.Failure("error")
            }

        })
    }

    fun testGiphy() {

    }


}