package com.example.scanner.features.api

import com.google.gson.annotations.SerializedName

// Body Request
data class VisionRequest(
    val requests: List<RequestItem>
)

data class RequestItem(
    val image: ImageData,
    val features: List<Feature>
)

data class ImageData(
    val content: String
)

data class Feature(
    val type: String,
    val maxResults: Int = 1
)

// Body Response
data class VisionResponse (
    val responses: List<Response>
)

data class Response (
    val labelAnnotations: List<LabelAnnotation>
)

data class LabelAnnotation (
    val mid: String,
    val description: String,
    val score: Double,
    val topicality: Double
)