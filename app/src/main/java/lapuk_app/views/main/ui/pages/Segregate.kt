package lapuk_app.views.main.ui.pages

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.lapuk_app.R
import lapuk_app.views.main.ui.theme.Typography
import lapuk_app.views.main.ui.theme.br1
import lapuk_app.views.main.ui.theme.br3
import lapuk_app.views.main.ui.theme.br4
import lapuk_app.views.main.ui.theme.br5
import java.io.File

@Composable
fun SegregatePage(navController: NavController) {
    val context = LocalContext.current

    val imageFiles = context.filesDir.listFiles { file -> file.extension == "png" } ?: emptyArray()
    val metaDataFiles =
        context.filesDir.listFiles { file -> file.extension == "txt" } ?: emptyArray()
    val itemCount = imageFiles.size

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn {
            items(itemCount) { index ->
                ColumnItem(imageFiles[index], metaDataFiles[index], navController)
            }
            item {
                Row {
                    if (itemCount <= 0) Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                    ) {
                        Text(
                            text = "No images added yet...",
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                    else Spacer(
                        modifier = Modifier
                            .height(80.dp)
                            .fillMaxWidth()
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(5.dp)
        ) {
            IconButton(modifier = Modifier
                .size(70.dp)
                .border(4.dp, br5, shape = RoundedCornerShape(20.dp))
                .shadow(elevation = 2.dp, shape = RoundedCornerShape(20.dp))
                .background(br3, shape = RoundedCornerShape(20.dp)), onClick = {
                navController.navigate("segregate/take-image") {
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }) {
                Icon(
                    modifier = Modifier.fillMaxSize(.85f),
                    painter = painterResource(id = R.drawable.add),
                    contentDescription = "add image",
                    tint = br5
                )
            }
        }
    }
}

@Composable
fun ColumnItem(file: File, metadata: File, navController: NavController) {
    val showDeleteDialog = remember { mutableStateOf(false) }
    val showPreviewDialog = remember { mutableStateOf(false) }
    val showMetadataDialog = remember { mutableStateOf(false) }
    val listDetections = remember { mutableStateOf<List<Pair<String, Float>>>(emptyList()) }
    val metadataContent = remember { mutableStateOf("") }

    fun readImageBitmap(file: File): ImageBitmap {
        return BitmapFactory.decodeFile(file.absolutePath).asImageBitmap()
    }

    fun loadDetections(file: File): List<Pair<String, Float>> {
        // Load detections from a file or any other source
        // This is a placeholder implementation
        return listOf(
            "Item 1" to 0.95f, "Item 2" to 0.85f
        )
    }

    fun readMetadata(file: File): String {
        return file.readText()
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .shadow(0.5.dp, shape = RoundedCornerShape(10.dp)),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = br1)
    ) {
        Box(modifier = Modifier.clickable {
            listDetections.value = loadDetections(file)
            showPreviewDialog.value = true
        }) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 17.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    bitmap = readImageBitmap(file),
                    contentDescription = "image preview",
                    modifier = Modifier
                        .size(80.dp)
                        .align(Alignment.CenterVertically),
                    alignment = Alignment.CenterStart,
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = file.name.removeSuffix(".png"),
                    modifier = Modifier
                        .height(30.dp)
                        .align(Alignment.CenterVertically),
                    style = Typography.bodyMedium,
                )

                IconButton(modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .height(90.dp)
                    .width(50.dp), onClick = {
                    showDeleteDialog.value = true
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.delete),
                        contentDescription = "delete image",
                        tint = br4
                    )
                }
            }
        }
    }

    if (showDeleteDialog.value) {
        AlertDialog(onDismissRequest = { showDeleteDialog.value = false },
            containerColor = br1,
            modifier = Modifier
                .fillMaxHeight(.73f)
                .requiredWidth(400.dp)
                .padding(20.dp),
            shape = RoundedCornerShape(15.dp),
            title = { Text(text = "Delete Image", style = Typography.labelLarge) },
            text = {
                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(.8f)
                    ) {
                        Image(
                            bitmap = readImageBitmap(file),
                            contentDescription = "image to delete",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }

                    Text(
                        text = "Are you sure you want to delete this image?",
                        style = Typography.bodyLarge
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    metadata.delete()
                    file.delete()
                    navController.navigate("segregate") { // refresh page after image deletion
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                    showDeleteDialog.value = false
                }) {
                    Text("Delete", style = Typography.labelMedium)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog.value = false }) {
                    Text("Cancel", style = Typography.labelMedium)
                }
            })
    }

    if (showPreviewDialog.value) {
        AlertDialog(onDismissRequest = { showPreviewDialog.value = false },
            containerColor = br1,
            title = { Text(text = "Preview", style = Typography.labelLarge) },
            modifier = Modifier
                .fillMaxHeight(.73f)
                .requiredWidth(400.dp)
                .padding(20.dp),
            shape = RoundedCornerShape(15.dp),
            text = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    Image(
                        bitmap = readImageBitmap(file),
                        contentDescription = "full view of image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Fit
                    )
                }

            },
            dismissButton = {
                TextButton(onClick = {
                    metadataContent.value = readMetadata(metadata)
                    showMetadataDialog.value = true
                }) {
                    Text("See Detections", style = Typography.labelMedium)
                }
            },
            confirmButton = {
                TextButton(onClick = { showPreviewDialog.value = false }) {
                    Text("Close", style = Typography.labelMedium)
                }
            })
    }

    if (showMetadataDialog.value) {
        AlertDialog(onDismissRequest = {
            showPreviewDialog.value = false
            showMetadataDialog.value = false
        },
            containerColor = br1,
            title = { Text(text = "Detections", style = Typography.labelLarge) },
            modifier = Modifier
                .fillMaxHeight(.73f)
                .requiredWidth(400.dp)
                .padding(20.dp),
            shape = RoundedCornerShape(15.dp),
            text = {
                LazyColumn(modifier= Modifier.fillMaxSize()) {
                    val detectionsList = metadataContent.value.split(",").map { it.trim() }
                    items(detectionsList) { item ->
                        Text(
                            text = item,
                            modifier = Modifier.padding(10.dp),
                            style = Typography.bodyMedium
                        )
                    }
                }

            },
            confirmButton = {
                TextButton(onClick = { showMetadataDialog.value = false }) {
                    Text("Back", style = Typography.labelMedium)
                }
            })
    }
}