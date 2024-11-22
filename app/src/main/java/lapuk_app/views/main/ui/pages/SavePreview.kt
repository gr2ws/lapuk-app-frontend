package lapuk_app.views.main.ui.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import lapuk_app.views.main.ui.theme.br1
import lapuk_app.views.main.ui.theme.br3
import lapuk_app.views.main.ui.theme.br5

@Composable
fun SavePreviewDialog(
    imageBitmap: ImageBitmap, onDismiss: (Boolean) -> Unit
) {
    Dialog(onDismissRequest = { onDismiss(false) }) {
        Card(
            modifier = Modifier
                .fillMaxHeight(.73f)
                .requiredWidth(400.dp)
                .padding(20.dp),
            colors = CardDefaults.cardColors(containerColor = br1),
            shape = RoundedCornerShape(15.dp)
        ) {
            Column {
                Image(
                    imageBitmap,
                    contentDescription = "Captured Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(.8f)
                        .padding(15.dp),
                    contentScale = ContentScale.Fit
                )
                Text(
                    "Save this classification?",
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    // Retake button
                    IconButton(modifier = Modifier
                        .height(50.dp)
                        .width(100.dp)
                        .border(2.dp, br5, shape = RoundedCornerShape(10.dp))
                        .shadow(2.dp, shape = RoundedCornerShape(10.dp))
                        .background(br3, shape = RoundedCornerShape(10.dp)), onClick = {

                        onDismiss(false)
                    }) {
                        Text(
                            text = "Retake", modifier = Modifier.padding(3.dp)
                        )
                    }

                    // Save button
                    IconButton(modifier = Modifier
                        .height(50.dp)
                        .width(100.dp)
                        .border(2.dp, br5, shape = RoundedCornerShape(10.dp))
                        .shadow(2.dp, shape = RoundedCornerShape(10.dp))
                        .background(br3, shape = RoundedCornerShape(10.dp)), onClick = {

                        onDismiss(true)
                    }) {
                        Text(
                            text = "Save", modifier = Modifier.padding(3.dp)
                        )
                    }
                }
            }
        }
    }
}