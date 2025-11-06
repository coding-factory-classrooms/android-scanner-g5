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
    Gif(id = "2", title = "Sport 2000", url = "https://www.sponsorportaal.nl/public/actionImage//cmp_sponsors_actions_52037d6716318.gif", date = Date()),
    Gif(id = "3", title = "McLaren", url = "https://media.tenor.com/O5spt09Ct8AAAAAM/mclaren.gif", date = Date()),
    Gif(id = "4", title = "mongole", url = "https://media.tenor.com/ILTtP8Iw4zMAAAAM/lol-drinking.gif", date = Date()),
    Gif(id = "5", title = "Zenitsu", url = "https://i.pinimg.com/originals/d3/b9/e4/d3b9e4f99c67ae36ef450c3fdc3df3a8.gif", date = Date()),
)

val sampleGif = (
        Gif(id = "1", title = "Eren", url = "https://i.redd.it/vjtnz6zrd79f1.gif", date = Date())
)
