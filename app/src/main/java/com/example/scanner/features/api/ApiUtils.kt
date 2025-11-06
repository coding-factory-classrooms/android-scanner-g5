package com.example.scanner.features.api


import android.util.Log
import io.paperdb.Paper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ApiUtils() {

    val retrofitGiphy = Retrofit.Builder()
        .baseUrl("https://api.giphy.com/v1/gifs/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val retrofitVision = Retrofit.Builder()
        .baseUrl("https://vision.googleapis.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    val apiGiphy = retrofitGiphy.create(GiphyApi::class.java)
    val apiVision = retrofitVision.create(VisionApi::class.java)


    // onComplete callback reports whether the flow ended successfully (true) or failed (false)
    fun searchVision(base64Image : String, onComplete: (Boolean) -> Unit = {}) {

        val request = VisionRequest(
            requests = listOf(
                RequestItem(
                    image = ImageData(content = base64Image),
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
                // forward the onComplete to searchGif so it can notify when done
                searchGif(visionResponse.responses[0].labelAnnotations[0].description, onComplete)
            }

            override fun onFailure(
                call: Call<VisionResponse?>,
                t: Throwable
            ) {
                Log.e("package:mine", "onFailure: ", t)
                onComplete(false)
            }

        })
    }

    fun searchGif(text : String, onComplete: (Boolean) -> Unit = {}) {

        // Call api
        val call = apiGiphy.getGif(
            key = "VDIEptOWtTjRGFWClKIQ5O5gzR3PxD7i",
            text = text
        )
        call.enqueue(object : Callback<GifList> {

            override fun onResponse(call: Call<GifList>, response: Response<GifList>) {
                val body = response.body()
                val gifsFromApi: MutableList<Gif> = body?.data ?: mutableListOf()

                val firstGif = gifsFromApi.firstOrNull()
                if (firstGif == null) {
                    Log.i("package:mine", "onResponse: no gifs in API response")
                    onComplete(false)
                    return
                }

                val existing: MutableList<Gif> = Paper.book().read<MutableList<Gif>>("gifs") ?: mutableListOf()

                val alreadyExists = existing.any { it.id == firstGif.id }
                if (alreadyExists) {
                    val now = System.currentTimeMillis()
                    val updatedList = existing.map { gif ->
                        if (gif.id == firstGif.id) gif.copy(updatedAt = now) else gif
                    }.toMutableList()
                    Paper.book().write("gifs", updatedList)
                    Log.i("package:mine", "onResponse: gif already present - updated 'updatedAt' for ${firstGif.id}")
                    onComplete(true)
                } else {
                    val now = System.currentTimeMillis()
                    val gifToAdd = firstGif.copy(updatedAt = now)
                    existing.add(0, gifToAdd)
                    Paper.book().write("gifs", existing)
                    Log.i("package:mine", "onResponse: added first gif ${firstGif.id} to store (new size=${existing.size})")
                    onComplete(true)
                }
            }

            override fun onFailure(call: Call<GifList>, t: Throwable) {
                Log.e("package:mine", "onFailure: ", t)
                onComplete(false)
            }

        })
    }
}