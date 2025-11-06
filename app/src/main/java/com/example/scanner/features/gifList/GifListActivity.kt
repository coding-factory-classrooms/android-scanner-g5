package com.example.scanner.features.gifList

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.scanner.features.detailGif.DetailGifView
import com.example.scanner.ui.theme.ScannerTheme

class GifListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            ScannerTheme {
                GifListView()
            }
        }
    }
}