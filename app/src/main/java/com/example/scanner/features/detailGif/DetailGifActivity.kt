package com.example.scanner.features.detailGif

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.scanner.ui.theme.ScannerTheme

class DetailGifActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val id = intent.getStringExtra("gifId") ?: ""

        enableEdgeToEdge()
        setContent {
            ScannerTheme {
                DetailGifView(gifId = id)
            }
        }
    }
}