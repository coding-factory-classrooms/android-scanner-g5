package com.example.scanner.features.gifList

import android.content.Intent
import android.util.Log
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.scanner.GifAnime
import com.example.scanner.features.detailGif.DetailGifActivity
import com.example.scanner.features.scan.CameraCaptureButton
import com.example.scanner.ui.theme.ScannerTheme

@Composable
fun GifCard(gif: Gif, onClick: (String) -> Unit) {
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .clickable{onClick(gif.id)}
            .padding(4.dp)
    ) {
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
    val context = LocalContext.current
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(items = gifs) { gif ->
            GifCard(gif, onClick = { id ->
                val intent = Intent(context, DetailGifActivity::class.java)
                intent.putExtra("gifId", id)
                context.startActivity(intent)
                println("click")
            })
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
            GifCard(gif = gif, onClick = { id ->
                println("click")
            })
        }
    }
}

@Composable
fun GifListView(vm: GifListViewModel = viewModel()) {

    val uiListState by vm.uiState.collectAsState()
    val gifsFlow by vm.gifFlow.collectAsState()
    val activity = LocalActivity.current

    LaunchedEffect(Unit) {
        vm.loadGif()
    }

    Scaffold() { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
        ) {
            Text(text = "Page Gif liste", fontSize = 20.sp)

            when (uiListState) {
                GifListUiState.Loading -> {
                    CircularProgressIndicator()
                    Text("loading")
                }
                is GifListUiState.Failure -> {
                    Text("Exeption${(uiListState as GifListUiState.Failure).message}")
                }

                is GifListUiState.Success -> {

                    GifListBody(gifsFlow, innerPadding)

                }
            }
            CameraCaptureButton(
                modifier = Modifier.align(Alignment.BottomEnd),
                text = "Open Cam",
                onResult = { base64 ->
                    vm.test()
                    Log.d("GifListView", "Image capture (base64 length=${base64.length})")
                },
                onError = { error ->
                    Log.e("GifListView", "Error camera: $error")
                }
            )
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