package lapuk_app.views.main.ui.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun SavePreviewDialog(
    imageBitmap: ImageBitmap,
    onDismiss: (Boolean) -> Unit) {

    Dialog(onDismissRequest = { onDismiss(false) }) {
        Card(
            modifier = Modifier
                .fillMaxWidth().fillMaxHeight(.65f),
            shape = RoundedCornerShape(20.dp)
        ) {
            Image(imageBitmap, contentDescription = "Captured Image")
        }
    }
}
