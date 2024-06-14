package com.example.myapplication.common.ui

import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.myapplication.feature.profile.AppTheme
import com.example.myapplication.feature.profile.ColorScheme
import kotlinx.coroutines.flow.MutableStateFlow

class UiRepository(
    private val sharedPreferences: SharedPreferences,
) {
    val appTheme = MutableStateFlow(AppTheme.System)
    val colorScheme = MutableStateFlow(ColorScheme.Default)

    init {
        initTheme()
    }

    fun setAppTheme(value: String) {
        sharedPreferences.edit {
            putString("appThemeKey", value)
        }
        initTheme()
    }

    fun setColorScheme(value: String) {
        sharedPreferences.edit {
            putString("colorSchemeKey", value)
        }
        initTheme()
    }

    private fun initTheme() {
        val theme = sharedPreferences.getString("appThemeKey", AppTheme.System.name).orEmpty()
        val color = sharedPreferences.getString("colorSchemeKey", ColorScheme.Default.name).orEmpty()
        appTheme.value = AppTheme.valueOf(theme)
        colorScheme.value = ColorScheme.valueOf(color)
    }
}