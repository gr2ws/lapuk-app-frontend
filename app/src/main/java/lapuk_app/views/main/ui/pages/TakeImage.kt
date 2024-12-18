package lapuk_app.views.main.ui.pages

import android.graphics.Bitmap
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavHostController
import com.example.lapuk_app.R
import lapuk_app.views.main.ShowCameraView
import lapuk_app.views.main.getPermission
import lapuk_app.views.main.hasPermission
import lapuk_app.views.main.takePhoto
import lapuk_app.views.main.ui.theme.Typography
import lapuk_app.views.main.ui.theme.br1
import lapuk_app.views.main.ui.theme.br2
import lapuk_app.views.main.ui.theme.br3
import lapuk_app.views.main.ui.theme.br4
import lapuk_app.views.main.ui.theme.br5


/**
 * Composable function that displays the Take Image Page.
 * This page allows the user to take or upload a picture of waste items.
 *
 * @param navController The NavHostController used for navigation.
 */
@Composable
fun TakeImagePage(navController: NavHostController) {
    var hasCameraPermission by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    var showStatsDialog by remember { mutableStateOf(false) }

    val imageDialog = remember { mutableStateOf<Bitmap?>(null) }

    val context = LocalContext.current

    val controller = remember {
        LifecycleCameraController(context).apply {
            setEnabledUseCases(CameraController.IMAGE_CAPTURE)
        }
    }

    val statsFile = context.filesDir.listFiles { file -> file.name == "stats.txt" }?.firstOrNull()

    @Suppress("DEPRECATION") val pick =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                imageDialog.value = MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
                showDialog = true
            } else {
                showDialog = false
            }
        }

    // Function to unbind camera use cases
    fun unbindCamera() {
        controller.unbind()
    }

    // rebind camera use cases
    @Composable
    fun rebindCamera() {
        controller.bindToLifecycle(LocalLifecycleOwner.current)
    }

    // Request camera permission when the composable is first composed
    LaunchedEffect(key1 = Unit) {
        if (hasPermission(context)) hasCameraPermission = true
        else { // if permission not granted, ask permission and check again
            getPermission(context)

            if (hasPermission(context)) hasCameraPermission = true

            navController.navigate("segregate/take-image") { // refresh page after giving permission
                popUpTo(navController.graph.startDestinationId) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        }
    }

    // Display camera preview if camera permission is granted
    if (hasCameraPermission && !showDialog) {
        ShowCameraView(
            controller = controller, modifier = Modifier
                .zIndex(-2f)
                .fillMaxSize()
        )
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
        )
    }

    Column {
        Row(
            modifier = Modifier
                .weight(.35f)
                .background(Color.Black.copy(alpha = 0.65f))
                .padding(horizontal = 15.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Back icon button
            IconButton(modifier = Modifier
                .size(70.dp)
                .border(3.dp, br5, shape = RoundedCornerShape(20.dp))
                .shadow(elevation = 2.dp, shape = RoundedCornerShape(20.dp))
                .background(br3, shape = RoundedCornerShape(20.dp)), onClick = {
                unbindCamera()
                navController.navigate("segregate") {
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }) {
                Icon(
                    modifier = Modifier.fillMaxSize(.45f),
                    painter = painterResource(id = R.drawable.arrow_left),
                    contentDescription = "return to segregate page",
                    tint = br5
                )
            }

            Spacer(modifier = Modifier.size(20.dp))

            // Instruction text
            Text(
                text = """
                    Take (or upload) a picture of waste items. Center the item within the frame and ensure it is well lit.
                """.trimIndent(),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp, top = 30.dp),
                color = Color.White,
                style = Typography.bodyMedium,
                lineHeight = 14.sp * 1.8
            )
        }

        // Camera preview box
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
                .border(3.dp, Color.White)
                .aspectRatio(1f)
                .weight(1f)
        )

        // Bottom camera buttons
        Box(
            modifier = Modifier
                .weight(.35f)
                .background(Color.Black.copy(alpha = 0.70f))
                .padding(15.dp)
                .fillMaxWidth()
        ) {
            // Capture image button
            IconButton(modifier = Modifier
                .size(80.dp)
                .border(3.dp, br5, shape = RoundedCornerShape(20.dp))
                .shadow(elevation = 2.dp, shape = RoundedCornerShape(20.dp))
                .background(br3, shape = RoundedCornerShape(20.dp))
                .align(alignment = Alignment.Center), onClick = {
                takePhoto(controller, context) { image ->
                    imageDialog.value = image
                    showDialog = true
                    unbindCamera()
                }
            }) {
                Icon(
                    modifier = Modifier.fillMaxSize(.45f),
                    painter = painterResource(id = R.drawable.camera),
                    contentDescription = "capture image",
                    tint = br5
                )
            }

            // Upload image button
            IconButton(modifier = Modifier
                .size(65.dp)
                .border(3.dp, br5, shape = RoundedCornerShape(20.dp))
                .shadow(elevation = 2.dp, shape = RoundedCornerShape(20.dp))
                .background(br3, shape = RoundedCornerShape(20.dp))
                .align(Alignment.CenterEnd),
                onClick = {
                    pick.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                }) {
                Icon(
                    modifier = Modifier.fillMaxSize(.4f),
                    painter = painterResource(id = R.drawable.upload),
                    contentDescription = "upload image from gallery",
                    tint = br5
                )
            }

            // statistics page button
            IconButton(modifier = Modifier
                .size(65.dp)
                .border(
                    3.dp,
                    if (statsFile != null) br5 else br4,
                    shape = RoundedCornerShape(20.dp)
                )
                .shadow(elevation = 2.dp, shape = RoundedCornerShape(20.dp))
                .background(
                    if (statsFile != null) br3 else br2,
                    shape = RoundedCornerShape(20.dp)
                )
                .align(Alignment.CenterStart),
                onClick = {
                    if (statsFile != null) {
                        showStatsDialog = true
                    } else {
                        Toast.makeText(context, "No data available.", Toast.LENGTH_SHORT).show()
                    }
                }) {
                Icon(
                    modifier = Modifier.fillMaxSize(.4f),
                    painter = painterResource(id = R.drawable.stats),
                    contentDescription = "upload image from gallery",
                    tint = if (statsFile != null) br5 else br4
                )
            }

            if (showDialog && imageDialog.value != null) {
                SavePreviewDialog(imageBitmap = imageDialog.value!!,
                    onDismiss = {
                        showDialog = it
                    })
                if (showDialog) rebindCamera()
            }

            if (showStatsDialog) {
                StatisticsCard { showStatsDialog = it }
            }

        }
    }
}

@Composable
fun StatisticsCard(onDismissDialog: (Boolean) -> Unit) {

    val context = LocalContext.current

    val stats = context.openFileInput("stats.txt").bufferedReader()
        .use { file ->
            file.readText().split(";").filter { it.isNotBlank() }.map { it.toInt() }
        }

    val detectionNames = listOf(
        "Battery",
        "Biological",
        "Cardboard",
        "Clothes",
        "Glass",
        "Metal",
        "Paper",
        "Plastic",
        "Sanitary Waste",
        "Shoes"
    )

    AlertDialog(onDismissRequest = {
        onDismissDialog(false)
    },
        containerColor = br1,
        title = { Text(text = "Total Detections", style = Typography.labelLarge) },
        modifier = Modifier
            .fillMaxHeight(.73f)
            .requiredWidth(400.dp)
            .padding(20.dp),
        shape = RoundedCornerShape(15.dp),
        text = {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(stats.size) { index ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = detectionNames[index],
                            style = Typography.bodyLarge
                        )
                        Text(
                            text = stats[index].toString(),
                            style = Typography.bodyLarge
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = { onDismissDialog(false) }) {
                Text("Back", style = Typography.labelMedium)
            }
        })
}