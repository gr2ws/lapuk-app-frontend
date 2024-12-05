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
    val itemCount = imageFiles.size

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn {
            items(itemCount) { index ->
                ColumnItem(imageFiles[index], navController)
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
fun ColumnItem(file: File, navController: NavController) {
    val showDeleteDialog = remember { mutableStateOf(false) }

    fun readImageBitmap(file: File): ImageBitmap {
        return BitmapFactory.decodeFile(file.absolutePath).asImageBitmap()
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .shadow(0.5.dp, shape = RoundedCornerShape(10.dp)),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = br1)
    ) {
        Box(modifier = Modifier.clickable { TODO("Open image") }) {
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
                    Image(
                        bitmap = readImageBitmap(file),
                        contentDescription = "image to delete",
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(.8f),
                        contentScale = ContentScale.Crop
                    )
                    Text(
                        text = "Are you sure you want to delete this image?",
                        style = Typography.bodyLarge
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = {
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
}