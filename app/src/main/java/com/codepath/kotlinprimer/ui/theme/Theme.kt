package com.codepath.kotlinprimer.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

val CodePathNavy      = Color(0xFF1B1F3B)
val CodePathTeal      = Color(0xFF00C896)
val CodePathBlue      = Color(0xFF4A7CE2)
val CodePathNavyLight = Color(0xFF2A2F52)
val BackgroundDark    = Color(0xFF13162B)
val SurfaceCard       = Color(0xFF1E2240)
val SurfaceBorder     = Color(0xFF2E3560)
val TextPrimary       = Color(0xFFE8EAF2)
val TextSecondary     = Color(0xFFB0B5CC)
val TextMuted         = Color(0xFF7A8099)
val CorrectGreen      = Color(0xFF3ECF8E)
val CorrectGreenBg    = Color(0x1A3ECF8E)
val WrongRed          = Color(0xFFE05C5C)
val WrongRedBg        = Color(0x1AE05C5C)
val CodeBg            = Color(0xFF0F1220)
val CodeText          = Color(0xFFCDD6F4)

private val DarkColorScheme = darkColorScheme(
    primary = CodePathTeal,
    onPrimary = CodePathNavy,
    secondary = CodePathBlue,
    background = BackgroundDark,
    surface = SurfaceCard,
    surfaceVariant = SurfaceBorder,
    onBackground = TextPrimary,
    onSurface = TextPrimary,
    onSurfaceVariant = TextSecondary,
    error = WrongRed,
    errorContainer = WrongRedBg,
    onErrorContainer = TextPrimary
)

@Composable
fun KotlinPrimerTheme(content: @Composable () -> Unit) {
    val colorScheme = DarkColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val activity = view.context as? Activity
            activity?.let {
                val window = it.window
                window.statusBarColor = colorScheme.background.toArgb()
                window.navigationBarColor = colorScheme.background.toArgb()
                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
