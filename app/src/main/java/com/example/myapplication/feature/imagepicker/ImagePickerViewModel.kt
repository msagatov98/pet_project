package com.example.myapplication.feature.imagepicker

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.core.android.ui.presentation.controller.DefaultScreenController
import com.example.myapplication.core.android.ui.presentation.controller.ScreenController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ImagePickerViewModel : ViewModel(),
    ScreenController<Any, ImagePickerState, Any> by DefaultScreenController(ImagePickerState()) {

    fun setUri(uri: Uri?) {
        viewModelScope.launch {
            delay(200)
            setState(uiState.value.copy(imageUri = uri))
        }
    }
}