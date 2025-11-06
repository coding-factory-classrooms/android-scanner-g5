package com.example.scanner.features.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface VisionApi {

    @Headers("Content-Type: application/json")
    @POST("v1/images:annotate")
    fun postVision(
        @Query("key") apiKey: String,
        @Body request: VisionRequest
    ): Call<VisionResponse>
}