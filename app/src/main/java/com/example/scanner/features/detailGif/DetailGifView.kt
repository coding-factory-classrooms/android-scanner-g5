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
import androidx.compose.ui.platform.LocalContext
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

                    GifAnime(uiState.gif.url)

                    Text(text = uiState.gif.title)

                    Text("Date d'importation : ${uiState.gif.importDatetime}")

                    Button(onClick = { activity?.finish() }) {
                        Text("B")
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