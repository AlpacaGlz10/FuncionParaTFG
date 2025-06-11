package com.example.funciona.ui.theme

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.edit

object ThemeState {
    var isDarkMode by mutableStateOf(false)
        private set

    fun saveTheme(context: Context) {
        isDarkMode = !isDarkMode

        val sharedPreferences = context.getSharedPreferences("theme_prefs", Context.MODE_PRIVATE)
        sharedPreferences.edit {
            putBoolean("is_dark_mode", isDarkMode)
        }
    }

    fun loadTheme(context: Context) {
        val sharedPreferences = context.getSharedPreferences("theme_prefs", Context.MODE_PRIVATE)
        isDarkMode = sharedPreferences.getBoolean("is_dark_mode", false)
    }
}