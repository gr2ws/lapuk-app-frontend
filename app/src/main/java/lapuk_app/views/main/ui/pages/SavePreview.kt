package lapuk_app.views.main.ui.pages

import android.graphics.Bitmap
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.withTimeout
import lapuk_app.views.main.decodeToBitmap
import lapuk_app.views.main.encodeBitmap
import lapuk_app.views.main.requestAnalysis
import lapuk_app.views.main.ui.theme.br1
import lapuk_app.views.main.ui.theme.br2
import lapuk_app.views.main.ui.theme.br3
import lapuk_app.views.main.ui.theme.br4
import lapuk_app.views.main.ui.theme.br5

@Composable
fun SavePreviewDialog(
    imageBitmap: Bitmap, onDismiss: (Boolean) -> Unit
) {
    val context = LocalContext.current
    val imageResult = remember { mutableStateOf<Bitmap?>(imageBitmap) }
    val isLoading = remember { mutableStateOf(true) }
    val isAnalysisSuccessful = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        try {
            withTimeout(30000) { // throws error if not finished in half minute
                requestAnalysis(encodedString = encodeBitmap(imageBitmap), callback = { result ->
                    try {
                        imageResult.value = decodeToBitmap(result)
                        isAnalysisSuccessful.value = true
                    } catch (e: Exception) {
                        isAnalysisSuccessful.value = false
                    } finally {
                        isLoading.value = false
                    }
                })
            }
        } catch (e: Exception) { // show error message, if any. reset imageResult to original image
            Toast.makeText(
                context, "Image Analysis Error: ${e.message}", Toast.LENGTH_SHORT
            ).show()

            isLoading.value = false
            isAnalysisSuccessful.value = false

            imageResult.value = imageBitmap
        }
    }

    if (isLoading.value) {
        LoadingDialog(onDismiss = {
            onDismiss(false)
            Toast.makeText(
                context, "Image Analysis Cancelled.", Toast.LENGTH_SHORT
            ).show()
        })
    }

    if (!isLoading.value) {
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
                        imageResult.value!!.asImageBitmap(),
                        contentDescription = "Captured Image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(.8f)
                            .padding(15.dp),
                        contentScale = ContentScale.Fit
                    )

                    Text(
                        "Save this classification?",
                        modifier = Modifier.align(Alignment.CenterHorizontally)
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
                        IconButton(
                            modifier = Modifier
                                .height(50.dp)
                                .width(100.dp)
                                .border(
                                    2.dp,
                                    if (isAnalysisSuccessful.value) br5 else br4,
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .shadow(2.dp, shape = RoundedCornerShape(10.dp))
                                .background(
                                    if (isAnalysisSuccessful.value) br3 else br2,
                                    shape = RoundedCornerShape(10.dp)
                                ),
                            enabled = isAnalysisSuccessful.value,
                            onClick = {
                                onDismiss(false)
                            }
                        ) {
                            Text(
                                text = "Save",
                                modifier = Modifier.padding(3.dp),
                                color = if (isAnalysisSuccessful.value) Color.Black else br5
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LoadingDialog(onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxSize(.3f)
                    .background(Color.Transparent),
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(80.dp),
                    color = Color.White,
                )
                Text(
                    "Analyzing image...\nTap anywhere to cancel.",
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(10.dp),
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}