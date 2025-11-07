package com.example.scanner.features.detailGif

import android.content.ContentValues.TAG
import android.os.Build
import android.util.Log
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withLink
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.example.scanner.features.api.sampleGif
import com.example.scanner.ui.theme.ScannerTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

@Composable
fun DetailGifView(vm: DetailGifViewModel = viewModel(), gifId: String) {

    val uiState by vm.uiState.collectAsState()

    LaunchedEffect(gifId) {
        Log.i(TAG, "DetailGifView: loadGif")
        vm.loadGif(gifId)
    }

    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
        ) {
            DetailGifViewBody(uiState)
        }
    }
}


@Composable
fun DetailGifViewBody(uiState: GifUiState) {
    val activity = LocalActivity.current

    // Helper to format epoch millis into a readable date/time string
    fun formatUpdatedAt(updatedAt: Long?): String {
        return updatedAt?.let {
            try {
                val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.FRANCE)
                sdf.format(Date(it))
            } catch (_: Exception) {
                it.toString()
            }
        } ?: "N/A"
    }

    fun relativeTime(updatedAt: Long?): String {
        if (updatedAt == null) return "N/A"
        val now = System.currentTimeMillis()
        val diff = now - updatedAt
        if (diff < 0) return "Dans le futur !!!"

        val days = TimeUnit.MILLISECONDS.toDays(diff)
        val hours = TimeUnit.MILLISECONDS.toHours(diff)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(diff)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(diff)

        return when {
            days > 0 -> {
                if (days == 1L) "il y a 1 jour" else "il y a ${days} jours"
            }
            hours > 0 -> {
                if (hours == 1L) "il y a 1 heure" else "il y a ${hours} heures"
            }
            minutes > 0 -> {
                if (minutes == 1L) "il y a 1 minute" else "il y a ${minutes} minutes"
            }
            seconds >= 0 -> {
                if (seconds <= 1L) "à l'instant" else "il y a ${seconds} secondes"
            }
            else -> "N/A"
        }
    }

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            when (uiState) {
                GifUiState.Loading -> {
                    CircularProgressIndicator()
                    Text("loading")
                }

                is GifUiState.Failure -> {
                    Text("Exeption${uiState.message}")
                }

                is GifUiState.Success -> {
                    Text("Page détail Gif", fontSize = 20.sp)

//                    GifAnime(uiState.gif.url)

                    Text(
                        buildAnnotatedString {
                            withLink(
                                LinkAnnotation.Url(
                                    uiState.gif.url,
                                    TextLinkStyles(style = SpanStyle(color = Color.Blue))
                                )
                            ) {
                                append(uiState.gif.title)
                            }

                        }
                    )

                    Text("Date d'importation : ${uiState.gif.importDatetime}")

                    Text("Updated : ${formatUpdatedAt(uiState.gif.updatedAt)}")
                    Text("(${relativeTime(uiState.gif.updatedAt)})")

                    Button(onClick = { activity?.finish() }) {
                        Text("Back to list")
                    }
                }
            }
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

class StatePreviewProvider : PreviewParameterProvider<GifUiState> {
    override val values: Sequence<GifUiState>
        get() = sequenceOf(
            GifUiState.Loading,
            GifUiState.Success(sampleGif),
            GifUiState.Failure("Error"),
        )
}

@Preview
@Composable
fun DetailGifViewPreview(
    @PreviewParameter(StatePreviewProvider::class) uiState: GifUiState,
    ){
    ScannerTheme() {
        DetailGifViewBody(uiState)
    }
}