package com.example.scanner.features.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GiphyApi {

    @GET("search")
    fun getGif(
        @Query("api_key") key: String,
        @Query("q") text: String,
        @Query("limit") limit: Int = 1,
        @Query("rating") rating: String = "pg-13",
        @Query("offset") offset: Int = 0,
        @Query("lang") lang: String = "en",
        @Query("bundle") bundle: String = "messaging_non_clips",
        ): Call<List<Gif>>

}