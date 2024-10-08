package com.example.myapplication.feature.imagepicker.screen

import android.net.Uri
import androidx.compose.runtime.Immutable

@Immutable
data class ImagePickerState(
    val imageUri: Uri? = null,
)
