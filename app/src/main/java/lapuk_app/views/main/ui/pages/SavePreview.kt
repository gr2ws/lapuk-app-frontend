package lapuk_app.views.main.ui.pages

import android.content.Context
import android.graphics.Bitmap
import android.icu.text.SimpleDateFormat
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull
import lapuk_app.views.main.AnalysisResults
import lapuk_app.views.main.decodeToBitmap
import lapuk_app.views.main.encodeBitmap
import lapuk_app.views.main.requestAnalysis
import lapuk_app.views.main.ui.theme.Typography
import lapuk_app.views.main.ui.theme.br1
import lapuk_app.views.main.ui.theme.br2
import lapuk_app.views.main.ui.theme.br3
import lapuk_app.views.main.ui.theme.br4
import lapuk_app.views.main.ui.theme.br5
import java.util.Date

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun SavePreviewDialog(
    imageBitmap: Bitmap, onDismiss: (Boolean) -> Unit
) {
    val context = LocalContext.current

    // result values
    val analysisResults = remember { mutableStateOf<AnalysisResults?>(null) }
    val imageResult = remember { mutableStateOf<Bitmap?>(imageBitmap) }
    val listDetections = remember { mutableStateOf<List<Pair<String, Float>>>(emptyList()) }

    // flags
    val isLoading = remember { mutableStateOf(true) }
    val isAnalysisSuccessful = remember { mutableStateOf(false) }
    val showDetectionsDialog = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        try {
            withTimeoutOrNull(30000) {
                // Launch requestAnalysis in a separate coroutine
                val result = withContext(Dispatchers.IO) {
                    suspendCancellableCoroutine<AnalysisResults?> { continuation ->
                        requestAnalysis(
                            encodedString = encodeBitmap(imageBitmap),
                            callback = { result ->
                                continuation.resume(result, null)
                            })
                    }
                }

                // Process the result if not null
                if (result != null) {
                    analysisResults.value = result
                    imageResult.value = decodeToBitmap(analysisResults.value!!.image)
                    listDetections.value = analysisResults.value!!.detections
                    isAnalysisSuccessful.value = true
                }
            } ?: run {
                isLoading.value = false
                isAnalysisSuccessful.value = false
                throw Exception("Unable to receive results after 30 seconds.")

            }
        } catch (e: Exception) {
            // Handle errors

            if (e.message?.contains("coroutine", ignoreCase = true) == false) {
                Toast.makeText(
                    context, "Image Analysis Error: ${e.message}", Toast.LENGTH_SHORT
                ).show()
            }
            isLoading.value = false
            isAnalysisSuccessful.value = false

            imageResult.value = imageBitmap
        } finally {
            isLoading.value = false // Ensure loading state is reset
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
        if (showDetectionsDialog.value) {
            DetectionsDialog(detections = listDetections.value,
                onDismiss = { showDetectionsDialog.value = false })
        } else {
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
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(15.dp)
                                .fillMaxHeight(.8f)
                        ) {
                            imageResult.value?.let { bitmap ->
                                Image(
                                    bitmap.asImageBitmap(),
                                    contentDescription = "Captured Image",
                                    contentScale = ContentScale.Fit
                                )
                            }
                        }
                        Text(
                            if (isAnalysisSuccessful.value) "Save this classification?" else "Analysis failed. Retake image.",
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(15.dp),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) { // retake image
                            IconButton(modifier = Modifier
                                .height(50.dp)
                                .width(90.dp)
                                .border(2.dp, br5, shape = RoundedCornerShape(10.dp))
                                .shadow(2.dp, shape = RoundedCornerShape(10.dp))
                                .background(br3, shape = RoundedCornerShape(10.dp)), onClick = {
                                onDismiss(false)
                            }) {
                                Text(
                                    text = "Retake", modifier = Modifier.padding(3.dp)
                                )
                            }
                            // show detections, disabled if analysis failed
                            IconButton(modifier = Modifier
                                .height(50.dp)
                                .width(120.dp)
                                .border(
                                    2.dp,
                                    if (isAnalysisSuccessful.value) br5 else br4,
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .shadow(2.dp, shape = RoundedCornerShape(10.dp))
                                .background(
                                    if (isAnalysisSuccessful.value) br3 else br2,
                                    shape = RoundedCornerShape(10.dp)
                                ), enabled = isAnalysisSuccessful.value, onClick = {
                                showDetectionsDialog.value = true
                            }) {
                                Text(
                                    text = "Detections",
                                    modifier = Modifier.padding(3.dp),
                                    color = if (isAnalysisSuccessful.value) Color.Black else br5
                                )
                            }

                            // save image, disabled if analysis failed
                            IconButton(modifier = Modifier
                                .height(50.dp)
                                .width(90.dp)
                                .border(
                                    2.dp,
                                    if (isAnalysisSuccessful.value) br5 else br4,
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .shadow(2.dp, shape = RoundedCornerShape(10.dp))
                                .background(
                                    if (isAnalysisSuccessful.value) br3 else br2,
                                    shape = RoundedCornerShape(10.dp)
                                ), enabled = isAnalysisSuccessful.value, onClick = {
                                onDismiss(false)
                                val fileName = "${listDetections.value.size}; ${
                                    SimpleDateFormat(
                                        "MM:dd:yy, HH:mm:ss", java.util.Locale.getDefault()
                                    ).format(Date())
                                }"

                                // save image
                                context.openFileOutput(
                                    "$fileName.png", Context.MODE_PRIVATE
                                ).use { stream ->
                                    imageResult.value?.compress(
                                        Bitmap.CompressFormat.PNG, 100, stream
                                    ) ?: imageBitmap.compress(
                                        Bitmap.CompressFormat.PNG, 100, stream
                                    )
                                }

                                // Save detections as text
                                context.openFileOutput(
                                    "$fileName.txt", Context.MODE_APPEND
                                ).use { stream ->
                                    var num = 1

                                    if (listDetections.value.isEmpty()) stream.write("No waste items detected.".toByteArray())
                                    else listDetections.value.forEach {
                                        stream.write(
                                            "Item $num: ${it.first.replaceFirstChar { letter -> letter.uppercase() }}: ${(it.second * 100).toInt()}% confidence.,".toByteArray()
                                        )
                                        num++
                                    }
                                }

                                Toast.makeText(
                                    context, "Image saved.", Toast.LENGTH_SHORT
                                ).show()

                            }) {
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

@Composable
fun DetectionsDialog(detections: List<Pair<String, Float>>, onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxHeight(.73f)
                .requiredWidth(400.dp)
                .padding(20.dp),
            colors = CardDefaults.cardColors(containerColor = br1),
            shape = RoundedCornerShape(15.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                Column(Modifier.fillMaxSize()) {
                    var numItems = 1
                    Text("Detections", style = Typography.titleSmall)
                    LazyColumn {
                        if (detections.isEmpty()) item {
                            Text(
                                text = "No waste items detected.", modifier = Modifier.padding(5.dp)
                            )
                        } else items(detections) { detection ->
                            Text(
                                text = "Item $numItems: ${detection.first.replaceFirstChar { it.uppercase() }}: ${(detection.second * 100).toInt()}% confidence.",
                                modifier = Modifier.padding(5.dp)
                            )
                            numItems++
                        }
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(
                        modifier = Modifier
                            .height(50.dp)
                            .width(90.dp)
                            .border(2.dp, br5, shape = RoundedCornerShape(10.dp))
                            .shadow(2.dp, shape = RoundedCornerShape(10.dp))
                            .background(br3, shape = RoundedCornerShape(10.dp))
                            .align(Alignment.End), onClick = onDismiss
                    ) {
                        Text(
                            text = "Back", modifier = Modifier.padding(3.dp)
                        )
                    }
                }
            }
        }
    }
}
