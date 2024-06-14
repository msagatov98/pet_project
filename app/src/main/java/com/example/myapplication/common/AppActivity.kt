package com.example.myapplication.common

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.myapplication.common.ui.AppViewModel
import com.example.myapplication.common.ui.theme.AppTheme
import com.example.myapplication.common.ui.EnableEdgeToEdgeEffect
import com.example.myapplication.common.ui.Navigation
import org.koin.androidx.viewmodel.ext.android.viewModel

class AppActivity : ComponentActivity() {

    private val appViewModel by viewModel<AppViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            val state by appViewModel.state.collectAsStateWithLifecycle()
            AppTheme(state = state) {
                EnableEdgeToEdgeEffect()
                Navigation()
            }
        }
    }
}
