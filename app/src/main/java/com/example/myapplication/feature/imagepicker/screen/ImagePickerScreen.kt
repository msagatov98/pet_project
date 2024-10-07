package com.example.myapplication.feature.imagepicker.screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.example.myapplication.core.ext.empty
import com.example.myapplication.core.ui.component.AppThemePreview
import com.example.myapplication.core.ui.component.ScreenPreview
import com.example.myapplication.core.ui.component.Spacer
import org.koin.androidx.compose.koinViewModel

@Composable
fun ImagePickerScreen() {
    val viewModel: ImagePickerViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    ImagePickerScreen(uiState = uiState, pickImage = viewModel::setUri)
}

@Composable
private fun ImagePickerScreen(
    uiState: ImagePickerState,
    pickImage: (Uri?) -> Unit
) {
    val imagePicker = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) {
        it?.let { pickImage(it) }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AnimatedVisibility(visible = uiState.imageUri != null) {
            Box {
                AnimatedContent(
                    label = String.empty,
                    targetState = uiState.imageUri,
                    transitionSpec = {
                        (fadeIn(animationSpec = tween(220, delayMillis = 90)))
                            .togetherWith(fadeOut(animationSpec = tween(90)))
                    }
                ) {
                    Image(
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        painter = rememberAsyncImagePainter(model = it),
                        modifier = Modifier
                            .size(300.dp)
                            .clip(RoundedCornerShape(16.dp)),
                    )
                }
                IconButton(onClick = { pickImage(null) }) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = null)
                }
            }
        }
        Spacer(size = 16.dp)
        Button(
            content = { Text(text = "Show BS") },
            onClick = {
                imagePicker.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly),
                )
            },
        )
    }
}

@ScreenPreview
@Composable
private fun ImagePickerScreenPreview() {
    AppThemePreview {
        ImagePickerScreen(
            uiState = ImagePickerState(),
            pickImage = { },
        )
    }
}