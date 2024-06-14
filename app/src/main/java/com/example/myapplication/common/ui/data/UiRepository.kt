package com.example.myapplication.common.ui.data

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.edit
import androidx.core.os.LocaleListCompat
import com.example.myapplication.common.ui.presentation.theme.AppTheme
import com.example.myapplication.common.ui.presentation.theme.ColorScheme
import kotlinx.coroutines.flow.MutableStateFlow

class UiRepository(
    private val sharedPreferences: SharedPreferences,
) {
    val appTheme = MutableStateFlow(AppTheme.System)
    val colorScheme = MutableStateFlow(ColorScheme.Default)
    val onBoardingSkipped = MutableStateFlow(sharedPreferences.getBoolean("onBoardingKey", false))

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

    fun setLanguage(languageTag: String) {
        val locale = LocaleListCompat.forLanguageTags(languageTag)
        AppCompatDelegate.setApplicationLocales(locale)
    }

    fun onBoardingSkipped() {
        sharedPreferences.edit {
            putBoolean("onBoardingKey", true)
        }
    }

    private fun initTheme() {
        val theme = sharedPreferences.getString("appThemeKey", AppTheme.System.name).orEmpty()
        val color = sharedPreferences.getString("colorSchemeKey", ColorScheme.Default.name).orEmpty()
        appTheme.value = AppTheme.valueOf(theme)
        colorScheme.value = ColorScheme.valueOf(color)
    }
}