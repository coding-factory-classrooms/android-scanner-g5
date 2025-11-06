package com.example.scanner.features.api


import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.scanner.features.gifList.Gif
import com.example.scanner.features.gifList.GifList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ApiUtils() {

    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.giphy.com/v1/gifs/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api = retrofit.create(GiphyApi::class.java)


    fun searchGif(text : String) {

        // Call api
        val call = api.getGif(
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