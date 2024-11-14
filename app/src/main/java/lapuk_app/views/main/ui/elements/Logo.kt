package lapuk_app.views.main.ui.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.lapuk_app.R

@Composable
fun ShowLogo(color: Color, withText: Boolean) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        if (withText) {
            Image(
                painter = painterResource(id = R.drawable.logo_w_name),
                contentDescription = "logo",
                colorFilter = ColorFilter.tint(color)
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "logo",
                colorFilter = ColorFilter.tint(color),
                modifier = Modifier.size(40.dp) // Adjust the size as needed
            )
        }
    }
}
