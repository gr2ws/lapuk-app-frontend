package lapuk_app.views.main.ui.pages

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap

@Composable
fun SavePreviewPage(imageBitmap: ImageBitmap) {
    Image(imageBitmap, contentDescription = "Captured Image")
}