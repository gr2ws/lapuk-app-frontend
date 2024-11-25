package lapuk_app.views.main.ui.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import lapuk_app.views.main.ui.theme.Typography
import lapuk_app.views.main.ui.theme.br3

@Preview(showBackground = true)
@Composable
fun SpeechBubble() {

    val options = listOf("FAQs", "About Us", "Contact Us", "Privacy Policy")

    Column(
        modifier = Modifier
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        options.forEachIndexed { index, option ->
            Box(
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(
                            topStart = if (index == 0) 8.dp else 0.dp,
                            topEnd = if (index == 0) 8.dp else 0.dp,
                            bottomStart = if (index == options.size - 1) 8.dp else 0.dp,
                            bottomEnd = if (index == options.size - 1) 8.dp else 0.dp
                        )
                    )

                    .border(
                        width = if (index == 1 || index == 2) 0.1.dp else 0.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(
                            topStart = if (index == 0) 8.dp else 0.dp,
                            topEnd = if (index == 0) 8.dp else 0.dp,
                            bottomStart = if (index == options.size - 1) 8.dp else 0.dp,
                            bottomEnd = if (index == options.size - 1) 8.dp else 0.dp
                        )
                    )
                    .background(br3)
                    .fillMaxWidth(fraction = 0.8f)
                    .padding(horizontal = 26.dp, vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = option,
                    style = Typography.bodyMedium,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}