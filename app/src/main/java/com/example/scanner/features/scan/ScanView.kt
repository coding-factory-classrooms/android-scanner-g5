package com.example.scanner.features.scan

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.util.Base64
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
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

    val defaultSize = 56.dp
    val finalModifier = Modifier.size(defaultSize).then(modifier)

    var lastBase64 by remember { mutableStateOf<String?>(null) }

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
        lastBase64 = base64
        onResult(base64)
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) cameraLauncher.launch(null)
        else onError?.invoke("Permission caméra refusée")
    }

    Surface(
        modifier = finalModifier
            .combinedClickable(
                onClick = {
                    val granted = ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.CAMERA
                    ) == PackageManager.PERMISSION_GRANTED

                    if (granted) cameraLauncher.launch(null)
                    else permissionLauncher.launch(Manifest.permission.CAMERA)
                },
                onLongClick = {
                    val current = lastBase64
                    if (current != null) onResult(current)
                    else onError?.invoke("Aucune image capturée à renvoyer")
                }
            ),
        shape = CircleShape,
        color = MaterialTheme.colorScheme.primary,
        tonalElevation = 4.dp
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Icon(
                imageVector = Icons.Filled.CameraAlt,
                contentDescription = text,
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}