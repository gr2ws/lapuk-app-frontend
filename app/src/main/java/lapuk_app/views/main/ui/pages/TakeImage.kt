package lapuk_app.views.main.ui.pages

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
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
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavHostController
import com.example.lapuk_app.R
import lapuk_app.views.main.getCameraProvider
import lapuk_app.views.main.ui.theme.br3
import lapuk_app.views.main.ui.theme.br5
import java.util.concurrent.Executors

/**
 * Composable function that displays the Take Image Page.
 * This page allows the user to take or upload a picture of waste items.
 *
 * @param navController The NavHostController used for navigation.
 */
@Composable
fun TakeImagePage(navController: NavHostController) {
    val context = LocalContext.current

    var hasCameraPermission by remember { mutableStateOf(false) }

    val previewView = remember {
        PreviewView(context)
    }

    var capturedImage: ImageProxy? = null

    // Launcher to request camera permission
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        hasCameraPermission = isGranted
    }

    // Request camera permission when the composable is first composed
    LaunchedEffect(key1 = Unit) {
        launcher.launch(Manifest.permission.CAMERA)

//        if (!hasCameraPermission) {
//            Toast.makeText(
//                context, "Permissions were not granted!", Toast.LENGTH_LONG
//            ).show()
//        }
    }

    // Display camera preview if permission is granted, otherwise show a black screen
    if (hasCameraPermission) {
        val lensFacing = CameraSelector.LENS_FACING_BACK
        val lifecycleOwner = LocalLifecycleOwner.current
        val preview = Preview.Builder().build()

        val cameraxSelector = CameraSelector.Builder().requireLensFacing(lensFacing).build()

        LaunchedEffect(lensFacing) {
            val cameraProvider = context.getCameraProvider()
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(lifecycleOwner, cameraxSelector, preview)
            preview.surfaceProvider = previewView.surfaceProvider
        }

        AndroidView(
            factory = { previewView }, modifier = Modifier
                .fillMaxSize()
                .zIndex(-1f)
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
                .background(Color.Black.copy(alpha = 0.70f))
                .padding(horizontal = 15.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Back icon button
            IconButton(modifier = Modifier
                .size(70.dp)
                .border(4.dp, br5, shape = RoundedCornerShape(20.dp))
                .shadow(elevation = 2.dp, shape = RoundedCornerShape(20.dp))
                .background(br3, shape = RoundedCornerShape(20.dp)), onClick = {
                navController.navigate("segregate") {
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }) {
                Icon(
                    modifier = Modifier.fillMaxSize(.85f),
                    painter = painterResource(id = R.drawable.arrow_left),
                    contentDescription = "return to segregate page",
                    tint = br5
                )
            }

            // Instruction text
            Text(
                text = """
                    Take (or upload) a picture of waste items. Center the item within the frame and ensure it is well lit.
                """.trimIndent(),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp, top = 30.dp),
                color = Color.White
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
                .border(4.5.dp, br5, shape = RoundedCornerShape(20.dp))
                .shadow(elevation = 2.dp, shape = RoundedCornerShape(20.dp))
                .background(br3, shape = RoundedCornerShape(20.dp))
                .align(alignment = Alignment.Center), onClick = {

                val imageCapture = ImageCapture.Builder().build()
                val cameraExecutor = Executors.newSingleThreadExecutor()

                imageCapture.takePicture(cameraExecutor,
                    object : ImageCapture.OnImageCapturedCallback() {
                        override fun onCaptureSuccess(image: ImageProxy) {
                            capturedImage = image
                        }

//                        override fun onError(exception: ImageCaptureException) {
//                            Toast.makeText(
//                                context, "Unable to capture image!", Toast.LENGTH_SHORT
//                            ).show()
//                        }
                    })

            }) {
                Icon(
                    modifier = Modifier.fillMaxSize(.63f),
                    painter = painterResource(id = R.drawable.camera),
                    contentDescription = "capture image",
                    tint = br5
                )
            }

            // Upload image button
            IconButton(modifier = Modifier
                .size(70.dp)
                .border(4.dp, br5, shape = RoundedCornerShape(20.dp))
                .shadow(elevation = 2.dp, shape = RoundedCornerShape(20.dp))
                .background(br3, shape = RoundedCornerShape(20.dp))
                .align(Alignment.CenterEnd),
                onClick = { TODO("upload image from gallery") }) {
                Icon(
                    modifier = Modifier.fillMaxSize(.55f),
                    painter = painterResource(id = R.drawable.upload),
                    contentDescription = "upload image from gallery",
                    tint = br5
                )
            }
        }
    }
}