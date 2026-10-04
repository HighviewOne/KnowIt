package com.knowit.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// The app is always dark; screens paint their own backgrounds and text colors.
private val ColorScheme = darkColorScheme(
    primary = KnowItPrimary,
    secondary = KnowItSecondary,
    tertiary = CorrectGreen,
    background = KnowItBackground,
    surface = KnowItSurface,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White
)

@Composable
fun KnowItTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = ColorScheme,
        typography = Typography,
        content = content
    )
}
