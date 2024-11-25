package lapuk_app.views.main.ui.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.dp
import androidx.graphics.shapes.RoundedPolygon
import androidx.graphics.shapes.toPath
import lapuk_app.views.main.ui.theme.Typography
import lapuk_app.views.main.ui.theme.br3

//@Preview
@Composable
fun SpeechBubble(onOptionClick: (String) -> Unit) {

    val options = listOf("FAQs", "About Us", "Contact Us", "Privacy Policy")

    // container (Box) for everything
    Box(
        modifier = Modifier.width(160.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            options.forEachIndexed { index, option ->
                Box(
                    modifier = Modifier
                        .clip(
                            RoundedCornerShape(
                                topStart = if (index == 0) 12.dp else 0.dp,
                                topEnd = if (index == 0) 12.dp else 0.dp,
                                bottomStart = if (index == options.size - 1) 12.dp else 0.dp,
                                bottomEnd = if (index == options.size - 1) 12.dp else 0.dp
                            )
                        )
                        .border(
                            width = if (index == 1 || index == 2) 0.1.dp else 0.dp,
                            color = Color.Black,
                        )
                        .fillMaxWidth(1f)
                        .background(br3)
                        .clickable { onOptionClick(option) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = option,
                        modifier = Modifier.padding(vertical = 12.dp, horizontal = 0.dp),
                        style = Typography.bodyMedium
                    )
                }
            }

            Box(
                modifier = Modifier
                    .padding(0.dp)
                    .background(color = Color.Transparent)
                    .offset(x = 20.dp, y = (-20).dp)
                    .drawWithCache {
                        val roundedPolygon = RoundedPolygon(
                            numVertices = 3,
                            radius = size.minDimension / 3,
                            centerX = size.width / 2,
                            centerY = size.height / 2,
                        )
                        val roundedPolygonPath = roundedPolygon
                            .toPath()
                            .asComposePath()
                        onDrawBehind {
                            rotate(degrees = 90f, pivot = Offset(size.width/2, size.height/2))
                            { drawPath(roundedPolygonPath, color = br3) }
                        }
                    }
                    .size(55.dp)
            )
        }
    }
}

