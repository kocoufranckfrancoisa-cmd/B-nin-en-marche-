package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = BeninGreenLight,
    onPrimary = Color.Black,
    primaryContainer = BeninGreenDark,
    onPrimaryContainer = Color.White,
    secondary = BeninGoldLight,
    onSecondary = Color.Black,
    secondaryContainer = BeninGoldDark,
    onSecondaryContainer = Color.White,
    tertiary = BeninRed,
    background = NeutralDark,
    onBackground = Color(0xFFE2E8F0),
    surface = SurfaceDark,
    onSurface = Color(0xFFF1F5F9),
    surfaceVariant = CardDark,
    onSurfaceVariant = Color(0xFFCBD5E1)
)

private val LightColorScheme = lightColorScheme(
    primary = BeninGreen,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFD1FAE5),
    onPrimaryContainer = Color(0xFF065F46),
    secondary = BeninGold,
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFFFEF3C7),
    onSecondaryContainer = Color(0xFF92400E),
    tertiary = BeninRed,
    background = NeutralLight,
    onBackground = Color(0xFF0F172A),
    surface = SurfaceLight,
    onSurface = Color(0xFF1E293B),
    surfaceVariant = CardLight,
    onSurfaceVariant = Color(0xFF475569)
)

@Composable
fun BeninTheme(
    darkModeTheme: Int = 0, // 0 = System, 1 = Light, 2 = Dark
    content: @Composable () -> Unit
) {
    val isDark = when (darkModeTheme) {
        1 -> false
        2 -> true
        else -> isSystemInDarkTheme()
    }

    val colorScheme = if (isDark) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
