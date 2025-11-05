package com.example.scanner

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.example.scanner.features.gifList.GifListView
import com.example.scanner.ui.theme.ScannerTheme
import io.paperdb.Paper

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Paper.init(this)

        enableEdgeToEdge()
        setContent {
            ScannerTheme {
                GifListView()
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ScannerTheme {
        Greeting("G5")
    }
}

@Composable
fun GifAnime(url: String) {
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
        modifier = Modifier
            .fillMaxWidth(), // prend toute la largeur disponible
        contentScale = ContentScale.Crop // ou Fit / FillWidth selon ton besoin
    )
}