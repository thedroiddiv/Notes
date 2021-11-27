package com.dxn.notes.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = AppBlue,
    onPrimary = Color.Black,
    background = DarkBlue,
    onBackground = AppWhite,
)

private val LightColorPalette = lightColors(
    primary = AppYellow,
    onPrimary = Color.Black,
    background = Color.White,
    onBackground = Color.Black,
)

@Composable
fun NotesTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}