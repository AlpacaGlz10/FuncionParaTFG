package com.example.funciona.ui.theme

import android.os.Build
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = LightRed,   // Rojo intenso
    onPrimary = Color.White,
    primaryContainer = IntenseRed,
    onPrimaryContainer = Color.White,

    secondary = Pink, // Rosa fuerte
    onSecondary = Color.White,
    secondaryContainer = DarkPink,
    onSecondaryContainer = Color.White,

    tertiary = Purple,  // Morado suave
    onTertiary = Color.White,
    tertiaryContainer = DarkPurple,
    onTertiaryContainer = Color.White,

    background = Color(0xFF121212),
    onBackground = Color.White,

    surface = Color(0xFF1E1E1E),
    onSurface = Color.White,
    surfaceVariant = Color(0xFF2C2C2C),
    onSurfaceVariant = Color.LightGray,

    outline = Color(0xFF888888)
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF1E88E5),   // Azul vibrante
    onPrimary = Color.White,
    primaryContainer = Color(0xFFD0EFFF),
    onPrimaryContainer = Color.Black,

    secondary = Color(0xFF42A5F5), // Azul medio
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFBDD8FF),
    onSecondaryContainer = Color.Black,

    tertiary = Color(0xFF66BB6A),  // Verde menta
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFE0F5EB),
    onTertiaryContainer = Color.Black,

    background = Color.White,
    onBackground = Color.Black,

    surface = Color(0xFFF9F9F9),
    onSurface = Color.Black,
    surfaceVariant = Color(0xFFE0E0E0),
    onSurfaceVariant = Color.Gray,

    outline = Color(0xFF757575)
)

@Composable
fun TSGAppTheme(
    darkTheme: Boolean = ThemeState.isDarkMode,
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}