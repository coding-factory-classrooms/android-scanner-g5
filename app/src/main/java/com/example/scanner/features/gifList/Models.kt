package com.example.scanner.features.gifList

import androidx.compose.material3.DatePickerState
import java.util.Date

data class Gif (
    val id: String,
    val title : String,
    val url : String,
    val date : Date
)

val samplesGif = listOf(
    Gif(id = "1", title = "Eren", url = "https://i.redd.it/vjtnz6zrd79f1.gif", date = Date()),
    Gif(id = "1", title = "Eren", url = "https://i.redd.it/vjtnz6zrd79f1.gif", date = Date()),
    Gif(id = "1", title = "Eren", url = "https://i.redd.it/vjtnz6zrd79f1.gif", date = Date()),
    Gif(id = "1", title = "Eren", url = "https://i.redd.it/vjtnz6zrd79f1.gif", date = Date()),
    Gif(id = "1", title = "Eren", url = "https://i.redd.it/vjtnz6zrd79f1.gif", date = Date()),
)

val sampleGif = (
        Gif(id = "1", title = "Eren", url = "https://i.redd.it/vjtnz6zrd79f1.gif", date = Date())
)
