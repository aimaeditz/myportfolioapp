package com.example.ui.theme

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
private fun animateColorScheme(target: ColorScheme): ColorScheme {
    val duration = 220
    val spec = tween<Color>(durationMillis = duration, easing = FastOutSlowInEasing)

    val primary by animateColorAsState(target.primary, animationSpec = spec, label = "primary")
    val onPrimary by animateColorAsState(target.onPrimary, animationSpec = spec, label = "onPrimary")
    val primaryContainer by animateColorAsState(target.primaryContainer, animationSpec = spec, label = "primaryContainer")
    val onPrimaryContainer by animateColorAsState(target.onPrimaryContainer, animationSpec = spec, label = "onPrimaryContainer")
    val secondary by animateColorAsState(target.secondary, animationSpec = spec, label = "secondary")
    val onSecondary by animateColorAsState(target.onSecondary, animationSpec = spec, label = "onSecondary")
    val secondaryContainer by animateColorAsState(target.secondaryContainer, animationSpec = spec, label = "secondaryContainer")
    val onSecondaryContainer by animateColorAsState(target.onSecondaryContainer, animationSpec = spec, label = "onSecondaryContainer")
    val tertiary by animateColorAsState(target.tertiary, animationSpec = spec, label = "tertiary")
    val onTertiary by animateColorAsState(target.onTertiary, animationSpec = spec, label = "onTertiary")
    val background by animateColorAsState(target.background, animationSpec = spec, label = "background")
    val onBackground by animateColorAsState(target.onBackground, animationSpec = spec, label = "onBackground")
    val surface by animateColorAsState(target.surface, animationSpec = spec, label = "surface")
    val onSurface by animateColorAsState(target.onSurface, animationSpec = spec, label = "onSurface")
    val surfaceVariant by animateColorAsState(target.surfaceVariant, animationSpec = spec, label = "surfaceVariant")
    val onSurfaceVariant by animateColorAsState(target.onSurfaceVariant, animationSpec = spec, label = "onSurfaceVariant")
    val outline by animateColorAsState(target.outline, animationSpec = spec, label = "outline")
    val outlineVariant by animateColorAsState(target.outlineVariant, animationSpec = spec, label = "outlineVariant")
    val error by animateColorAsState(target.error, animationSpec = spec, label = "error")
    val onError by animateColorAsState(target.onError, animationSpec = spec, label = "onError")
    val errorContainer by animateColorAsState(target.errorContainer, animationSpec = spec, label = "errorContainer")
    val onErrorContainer by animateColorAsState(target.onErrorContainer, animationSpec = spec, label = "onErrorContainer")

    return target.copy(
        primary = primary,
        onPrimary = onPrimary,
        primaryContainer = primaryContainer,
        onPrimaryContainer = onPrimaryContainer,
        secondary = secondary,
        onSecondary = onSecondary,
        secondaryContainer = secondaryContainer,
        onSecondaryContainer = onSecondaryContainer,
        tertiary = tertiary,
        onTertiary = onTertiary,
        background = background,
        onBackground = onBackground,
        surface = surface,
        onSurface = onSurface,
        surfaceVariant = surfaceVariant,
        onSurfaceVariant = onSurfaceVariant,
        outline = outline,
        outlineVariant = outlineVariant,
        error = error,
        onError = onError,
        errorContainer = errorContainer,
        onErrorContainer = onErrorContainer
    )
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

    val targetColorScheme = if (isDark) {
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

    val animatedColorScheme = animateColorScheme(targetColorScheme)

    MaterialTheme(
        colorScheme = animatedColorScheme,
        typography = Typography,
        content = content
    )
}
