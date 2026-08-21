package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

fun parseHexColor(hex: String, fallback: Color = DarkTertiaryGold): Color {
    return try {
        val clean = hex.removePrefix("#")
        if (clean.length == 6) {
            Color(android.graphics.Color.parseColor("#$clean"))
        } else if (clean.length == 8) {
            Color(android.graphics.Color.parseColor("#$clean"))
        } else {
            fallback
        }
    } catch (e: Exception) {
        fallback
    }
}

@Composable
fun MyPortfolioTheme(
    themeMode: String = "LIGHT", // "LIGHT", "DARK", "SYSTEM"
    accentHex: String = "#E9C176",
    content: @Composable () -> Unit
) {
    val isDark = when (themeMode.uppercase()) {
        "DARK" -> true
        "SYSTEM" -> isSystemInDarkTheme()
        else -> false // default LIGHT (White)
    }

    val accentColor = parseHexColor(accentHex, if (isDark) DarkTertiaryGold else LightTertiaryGold)

    val colorScheme = if (isDark) {
        darkColorScheme(
            primary = DarkPrimary,
            onPrimary = DarkOnPrimary,
            primaryContainer = DarkSurfaceContainerHigh,
            onPrimaryContainer = DarkOnSurface,
            secondary = DarkPrimary,
            onSecondary = DarkOnPrimary,
            secondaryContainer = DarkSurfaceContainerHighest,
            onSecondaryContainer = DarkOnSurfaceVariant,
            tertiary = accentColor,
            onTertiary = DarkOnPrimary,
            background = DarkBackground,
            onBackground = DarkOnSurface,
            surface = DarkSurface,
            onSurface = DarkOnSurface,
            surfaceVariant = DarkSurfaceContainerHighest,
            onSurfaceVariant = DarkOnSurfaceVariant,
            outline = DarkOutline,
            outlineVariant = DarkOutlineVariant,
            error = DarkError,
            onError = DarkOnPrimary,
            errorContainer = DarkErrorContainer,
            onErrorContainer = DarkOnSurface
        )
    } else {
        lightColorScheme(
            primary = LightPrimary,
            onPrimary = LightOnPrimary,
            primaryContainer = LightSurfaceContainerHigh,
            onPrimaryContainer = LightOnSurface,
            secondary = LightPrimary,
            onSecondary = LightOnPrimary,
            secondaryContainer = LightSurfaceContainerHighest,
            onSecondaryContainer = LightOnSurfaceVariant,
            tertiary = accentColor,
            onTertiary = LightOnPrimary,
            background = LightBackground,
            onBackground = LightOnSurface,
            surface = LightSurface,
            onSurface = LightOnSurface,
            surfaceVariant = LightSurfaceContainerHighest,
            onSurfaceVariant = LightOnSurfaceVariant,
            outline = LightOutline,
            outlineVariant = LightOutlineVariant,
            error = LightError,
            onError = LightOnPrimary,
            errorContainer = LightErrorContainer,
            onErrorContainer = LightOnSurface
        )
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
