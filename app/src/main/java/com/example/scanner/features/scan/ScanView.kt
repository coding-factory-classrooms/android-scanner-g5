package com.example.scanner.features.scan

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.util.Base64
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import java.io.ByteArrayOutputStream

@Composable
fun CameraCaptureButton(
    modifier: Modifier = Modifier,
    text: String = "Prendre une photo",
    onResult: (base64: String) -> Unit,
    onError: ((String) -> Unit)? = null
) {
    val context = LocalContext.current

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->
        if (bitmap == null) {
            onError?.invoke("Aucune image")
            return@rememberLauncherForActivityResult
        }
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream)
        val base64 = Base64.encodeToString(stream.toByteArray(), Base64.DEFAULT)
        onResult(base64)
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) cameraLauncher.launch(null)
        else onError?.invoke("Permission caméra refusée")
    }

    Button(modifier = modifier, onClick = {
        val granted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED

        if (granted) cameraLauncher.launch(null)
        else permissionLauncher.launch(Manifest.permission.CAMERA)
    }) {
        Text(text = text)
    }
}

@Composable
fun ScanView() {
    var base64 by remember { mutableStateOf<String?>(null) }
    var error by remember { mutableStateOf<String?>(null) }

    Scaffold { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            CameraCaptureButton(onResult = {
                base64 = it
                error = null
            }, onError = { err ->
                error = err
            })

            error?.let { Text(text = "Erreur: $it") }

            base64?.let {
                // On affiche juste un aperçu court pour ne pas polluer l'UI
                val preview = if (it.length > 120) it.take(120) + "..." else it
                Text(text = "Base64 (aperçu): $preview")
            }
        }
    }
}
