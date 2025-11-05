package com.example.scanner

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.example.scanner.ui.theme.ScannerTheme
import io.paperdb.Paper

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Paper.init(this)

        enableEdgeToEdge()
        setContent {
            ScannerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "G5",
//                        modifier = Modifier.padding(innerPadding)
//                    )
                    GifAnime("https://media3.giphy.com/media/v1.Y2lkPTc5MGI3NjExamd4Zzlwa3Z3YzZ2cmc5eGpoNWRtcHVwbWNrcXAyaWg0dTdlaW0zciZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/lS6PdcHrKAsjoBql8J/giphy.gif")
                }
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