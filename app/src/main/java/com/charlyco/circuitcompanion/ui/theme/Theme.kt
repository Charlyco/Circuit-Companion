package com.charlyco.circuitcompanion.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = AppColor1,
    primaryVariant = AppColor1Light,
    secondary = AppColor2,
    secondaryVariant = AppColor2Light,
    background = White

)

private val LightColorPalette = lightColors(
    primary = AppColor1,
    primaryVariant = AppColor1Light,
    secondary = AppColor2,
    secondaryVariant = AppColor2Light,
    background = White,
    surface = SemiTransparent,
    onPrimary = AppColor4Light,
    onSecondary = AppColor3Light,
    onBackground = Black,


    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun CircuitCompanionTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
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