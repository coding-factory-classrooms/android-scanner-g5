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
    Gif(id = "1", title = "Eren", url = "https://media3.giphy.com/media/v1.Y2lkPTZjMDliOTUybmRsMDVrMm10ZmgycWR0ZWxpYXNrcWZxbWtzc2l6YzBqMGI1NDR4ZCZlcD12MV9naWZzX3NlYXJjaCZjdD1n/uBuzWfwVcadRC/giphy.gif", date = Date()),
    Gif(id = "1", title = "Eren", url = "https://user-images.githubusercontent.com/6876788/96633009-d1818000-1318-11eb-9f1d-7f914f4ccb16.gif", date = Date()),
    Gif(id = "1", title = "Eren", url = "https://i.redd.it/vjtnz6zrd79f1.gif", date = Date()),
    Gif(id = "1", title = "Eren", url = "https://media3.giphy.com/media/v1.Y2lkPTc5MGI3NjExamd4Zzlwa3Z3YzZ2cmc5eGpoNWRtcHVwbWNrcXAyaWg0dTdlaW0zciZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/lS6PdcHrKAsjoBql8J/giphy.gif", date = Date()),
)
