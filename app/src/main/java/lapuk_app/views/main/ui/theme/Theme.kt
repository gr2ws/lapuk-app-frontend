package lapuk_app.views.main.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val br1 = Color(0xFFEDE0D4) //EDE0D4
val br2 = Color(0xFFE6CCB2) //E6CCB2
val br3 = Color(0xFFDDB892) //DDB892
val br4 = Color(0xFFB08968) //B08968
val br5 = Color(0xFF7F5539) //7F5539
val br6 = Color(0xFF9C6644) //9C6644
val wh1 = Color(0xFFF5F5F5) //F5F5F5

// Define the color scheme for the light (default) theme
private val colorScheme = lightColorScheme(
    primary = br5,
    onPrimary = Color.White,

    secondary = br6,
    onSecondary = Color.White,

    tertiary = br5,
    onTertiary = Color.White,

    background = br1,
    onBackground = Color.Black,

    surface = br2, // menu backgrounds
    onSurface = Color.Black

)

/**
 * Composable function to apply the Lapuk theme to the content.
 *
 * @param content The composable content to which the theme will be applied.
 */
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