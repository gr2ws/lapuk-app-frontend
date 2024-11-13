package lapuk_app.views.main.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val br1 = Color(0xFFEDE0D4)
val br2 = Color(0xFFE6CCB2)
val br3 = Color(0xFFDDB892)
val br4 = Color(0xFFB08968)
val br5 = Color(0xFF7F5539)
val br6 = Color(0xFF9C6644)

private val colorScheme = lightColorScheme(
    primary = br5, // headers, selected items, buttons
    onPrimary = Color.White,

    secondary = br6, // less prominent ui
    onSecondary = Color.White,

    tertiary = br5, // accents
    onTertiary = Color.White,

    background = br1, // bg
    onBackground = Color.Black,

    surface = br2, // menu backgrounds
    onSurface = Color.Black,
)

@Composable
fun LapukTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

