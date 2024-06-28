package com.example.myapplication.core.android.ui.data

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.edit
import androidx.core.os.LocaleListCompat
import com.example.myapplication.core.android.ui.presentation.theme.AppTheme
import com.example.myapplication.core.android.ui.presentation.theme.ColorScheme
import kotlinx.coroutines.flow.MutableStateFlow

private const val APP_THEME_KEY = "appThemeKey"
private const val COLOR_SCHEME_KEY = "colorSchemeKey"
private const val ON_BOARDING_KEY = "onBoardingKey"

class UiRepository(
    private val sharedPreferences: SharedPreferences,
) {
    val appTheme = MutableStateFlow(AppTheme.System)
    val colorScheme = MutableStateFlow(ColorScheme.Default)
    val onBoardingSkipped = MutableStateFlow(sharedPreferences.getBoolean(ON_BOARDING_KEY, false))

    init {
        initTheme()
    }

    fun setAppTheme(value: String) {
        sharedPreferences.edit {
            putString(APP_THEME_KEY, value)
        }
        initTheme()
    }

    fun setColorScheme(value: String) {
        sharedPreferences.edit {
            putString(COLOR_SCHEME_KEY, value)
        }
        initTheme()
    }

    fun setLanguage(languageTag: String) {
        val locale = LocaleListCompat.forLanguageTags(languageTag)
        AppCompatDelegate.setApplicationLocales(locale)
    }

    fun onBoardingSkipped() {
        sharedPreferences.edit {
            putBoolean(ON_BOARDING_KEY, true)
        }
    }

    private fun initTheme() {
        val theme = sharedPreferences.getString(APP_THEME_KEY, AppTheme.System.name).orEmpty()
        val color =
            sharedPreferences.getString(COLOR_SCHEME_KEY, ColorScheme.Default.name).orEmpty()
        appTheme.value = AppTheme.valueOf(theme)
        colorScheme.value = ColorScheme.valueOf(color)
    }
}