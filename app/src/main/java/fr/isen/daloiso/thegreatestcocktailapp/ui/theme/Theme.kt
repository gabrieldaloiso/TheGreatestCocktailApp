package fr.isen.daloiso.thegreatestcocktailapp.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = MidnightAmber,
    secondary = MidnightLightText,
    background = MidnightDark,
    surface = MidnightSlate,
    onPrimary = MidnightDark,
    onSecondary = MidnightDark,
    onBackground = MidnightLightText,
    onSurface = MidnightLightText
)

private val LightColorScheme = lightColorScheme(
    primary = MidnightAmber,
    secondary = MidnightDark,
    background = MidnightLightText,
    surface = MidnightSlate,
    onPrimary = MidnightLightText,
    onSecondary = MidnightLightText,
    onBackground = MidnightDark,
    onSurface = MidnightDark
)

@Composable
fun TheGreatestCocktailAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
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