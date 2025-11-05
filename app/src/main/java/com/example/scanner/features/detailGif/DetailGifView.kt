package com.example.scanner.features.detailGif

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.example.scanner.ui.theme.ScannerTheme
import androidx.activity.compose.LocalActivity


@Composable
fun DetailGifView() {

    val activity = LocalActivity.current
    Scaffold() { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(text = "Page détail Gif", fontSize = 20.sp)

//            Button( onClick = ) {
//                Text("Fav")
//            }

            GifAnime("https://media3.giphy.com/media/v1.Y2lkPTc5MGI3NjExamd4Zzlwa3Z3YzZ2cmc5eGpoNWRtcHVwbWNrcXAyaWg0dTdlaW0zciZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/lS6PdcHrKAsjoBql8J/giphy.gif")

            Text("title")

            Text("Date d'importation :")

            Button( onClick = {
                activity?.finish()
            }) {
                Text("Back")
            }

//            Button( onClick = ) {
//                Text("Delete")
//            }

        }

    }
}


@Composable
fun GifAnime(url: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current

    AsyncImage(
        model = ImageRequest.Builder(context)
            .data(url)
            .decoderFactory(
                if (Build.VERSION.SDK_INT >= 28) ImageDecoderDecoder.Factory()
                else GifDecoder.Factory()
            )
            .build(),
        contentDescription = null,
        modifier = modifier
    )

}

@Preview
@Composable
fun DetailGifViewPreview(){
    ScannerTheme() {
        DetailGifView()
    }
}