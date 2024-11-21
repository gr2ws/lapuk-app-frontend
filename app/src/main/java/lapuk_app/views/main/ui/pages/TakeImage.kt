package lapuk_app.views.main.ui.pages

import android.Manifest
import android.content.Context
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.example.lapuk_app.R
import lapuk_app.views.main.ui.theme.br3
import lapuk_app.views.main.ui.theme.br5

// https://medium.com/@deepugeorge2007travel/mastering-camerax-in-jetpack-compose-a-comprehensive-guide-for-android-developers-92ec3591a189

@Composable
fun TakeImagePage(navController: NavHostController) {
    val context = LocalContext.current
    var hasCameraPermission by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        hasCameraPermission = isGranted
    }

    LaunchedEffect(key1 = Unit) { // Request permission on composition
        launcher.launch(Manifest.permission.CAMERA)
    }

    if (hasCameraPermission) CameraPreview(context)
    else Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    )

    Column {
        Row( // above camera contents
            modifier = Modifier
                .weight(.35f)
                .background(Color.Black.copy(alpha = 0.5f))
                .padding(horizontal = 15.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(modifier = Modifier // back icon
                .size(70.dp)
                .border(3.dp, Color.White, shape = RoundedCornerShape(20.dp))
                .background(Color.Transparent), onClick = {
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
                    tint = Color.White
                )
            }
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

        Box( // camera preview center
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
                .border(3.dp, Color.White)
                .aspectRatio(1f)
                .weight(1f)
        )

        Box( // bottom camera buttons
            modifier = Modifier
                .weight(.35f)
                .background(Color.Black.copy(alpha = 0.5f))
                .padding(15.dp)
                .fillMaxWidth()
        ) {
            IconButton( // capture image button
                modifier = Modifier
                    .size(80.dp)
                    .border(4.5.dp, br5, shape = RoundedCornerShape(20.dp))
                    .background(br3, shape = RoundedCornerShape(20.dp))
                    .align(alignment = Alignment.Center), onClick = { TODO("capture image") }) {
                Icon(
                    modifier = Modifier.fillMaxSize(.63f),
                    painter = painterResource(id = R.drawable.camera),
                    contentDescription = "capture image",
                    tint = br5
                )
            }

            IconButton( // upload image button
                modifier = Modifier
                    .size(70.dp)
                    .border(4.dp, br5, shape = RoundedCornerShape(20.dp))
                    .background(br3, shape = RoundedCornerShape(20.dp)).align(Alignment.CenterEnd),
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

@Composable
fun CameraPreview(context: Context) {
    val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current
    var cameraProvider: ProcessCameraProvider? by remember { mutableStateOf(null) }

    AndroidView(factory = {
        val previewView = PreviewView(context).apply {
            this.scaleType = PreviewView.ScaleType.FILL_CENTER
            implementationMode = PreviewView.ImplementationMode.COMPATIBLE
        }
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        cameraProviderFuture.addListener({
            cameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build().also {
                it.surfaceProvider = previewView.surfaceProvider
            }
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            try {
                cameraProvider?.unbindAll()
                cameraProvider?.bindToLifecycle(
                    lifecycleOwner, cameraSelector, preview
                )
            } catch (e: Exception) {
                Log.e("CameraPreview", "Use case binding failed", e)
            }
        }, ContextCompat.getMainExecutor(context))
        previewView
    })
}