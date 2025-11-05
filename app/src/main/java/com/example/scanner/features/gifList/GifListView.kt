package com.example.scanner.features.gifList

import android.os.Build
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.scanner.ui.theme.ScannerTheme
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.example.scanner.GifAnime

@Composable
fun GifCard(gif: Gif) {
    Card {
        Column(Modifier.fillMaxWidth().padding(16.dp)) {
            GifAnime(
                url = gif.url,
            )
            Text(gif.title, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
            //Text(gif.url)
        }
    }
}

@Composable
fun GifListBody(gifs: List<Gif>, innerPadding: PaddingValues){
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(items = gifs) { gif ->
            GifCard(gif)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GifGrid(gifs: List<Gif>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2), // 2 colonnes
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(gifs) { gif ->
            GifCard(gif = gif)
        }
    }
}

@Composable
fun GifListView() {
    Scaffold() { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(text = "Page Gif liste", fontSize = 20.sp)

            GifListBody(samplesGif, innerPadding)
            //GifGrid(samplesGif)
        }
    }
}

@Preview
@Composable
fun GifListViewPreview(){
    ScannerTheme() {
        GifListView()
    }
}