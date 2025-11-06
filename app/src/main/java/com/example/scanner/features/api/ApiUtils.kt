package com.example.scanner.features.api


import android.util.Log
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


    fun searchVision(base64Image : String) {

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
                searchGif(visionResponse.responses[0].labelAnnotations[0].description)
            }

            override fun onFailure(
                call: Call<VisionResponse?>,
                t: Throwable
            ) {
                Log.e("package:mine", "onFailure: ", t)
            }

        })
    }

    fun searchGif(text : String) {

        // Call api
        val call = apiGiphy.getGif(
            key = "VDIEptOWtTjRGFWClKIQ5O5gzR3PxD7i",
            text = text
        )
        call.enqueue(object : Callback<GifList> {

            override fun onResponse(call: Call<GifList>, response: Response<GifList>) {
                val gif = response.body()!!
                Log.i("package:mine", "onResponse: $gif")
            }

            override fun onFailure(call: Call<GifList>, t: Throwable) {
                Log.e("package:mine", "onFailure: ", t)
            }

        })
    }
}