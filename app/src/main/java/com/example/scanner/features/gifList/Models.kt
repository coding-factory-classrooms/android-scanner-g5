package com.example.scanner.features.gifList

import com.google.gson.annotations.SerializedName


data class GifList(
    val data: List<Gif>,
)

data class Gif (
    val id: String,
    val url: String,
    val title: String,
    @SerializedName("import_datetime")
    val importDatetime: String
)

val samplesGif = listOf(
    Gif(id = "1", title = "Eren", url = "https://i.redd.it/vjtnz6zrd79f1.gif", importDatetime = "2025-08-07 01:56:11"),
    Gif(id = "2", title = "Sport 2000", url = "https://www.sponsorportaal.nl/public/actionImage//cmp_sponsors_actions_52037d6716318.gif", importDatetime = "2025-08-07 01:56:11"),
    Gif(id = "3", title = "McLaren", url = "https://media.tenor.com/O5spt09Ct8AAAAAM/mclaren.gif", importDatetime = "2025-08-07 01:56:11"),
    Gif(id = "4", title = "mongole", url = "https://media.tenor.com/ILTtP8Iw4zMAAAAM/lol-drinking.gif", importDatetime = "2025-08-07 01:56:11"),
    Gif(id = "5", title = "Zenitsu", url = "https://i.pinimg.com/originals/d3/b9/e4/d3b9e4f99c67ae36ef450c3fdc3df3a8.gif", importDatetime = "2025-08-07 01:56:11"),
)

val sampleGif = (
        Gif(id = "1", title = "Eren", url = "https://i.redd.it/vjtnz6zrd79f1.gif", importDatetime = "2025-08-07 01:56:11")
)
