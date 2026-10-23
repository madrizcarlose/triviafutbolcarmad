package com.example.triviafutbol.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = PitchGreen,
    onPrimary = PitchBlack,
    primaryContainer = DarkGreenContainer,
    onPrimaryContainer = TextPrimary,
    secondary = GoldStreak,
    onSecondary = PitchBlack,
    background = PitchBlack,
    onBackground = TextPrimary,
    surface = DarkCardSurface,
    onSurface = TextPrimary,
    surfaceVariant = DarkCardSurface,
    onSurfaceVariant = TextSecondary,
    outline = SurfaceBorder,
    error = RedCard,
    onError = TextPrimary
)

@Composable
fun TriviafutbolTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}
