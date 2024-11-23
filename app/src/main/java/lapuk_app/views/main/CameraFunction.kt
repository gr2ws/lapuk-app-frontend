package lapuk_app.views.main

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Matrix
import android.util.Log
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner

/**
 * A class that provides camera-related functions.
 */
class CameraFunction {
    /**
     * Checks if the camera permission is granted.
     *
     * @param context The context to use for checking the permission.
     * @return True if the camera permission is granted, false otherwise.
     */
    fun hasPermission(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context, Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    /**
     * Requests the camera permission.
     *
     * @param context The context to use for requesting the permission.
     */
    fun getPermission(context: Context) {
        ActivityCompat.requestPermissions(
            context as Activity, arrayOf(Manifest.permission.CAMERA), 0
        )
    }

    /**
     * Displays the camera view using the given controller.
     *
     * @param controller The LifecycleCameraController to control the camera.
     * @param modifier The Modifier to be applied to the view.
     */
    @Composable
    fun ShowCameraView(
        controller: LifecycleCameraController, modifier: Modifier
    ) {
        val lifecycleOwner = LocalLifecycleOwner.current
        AndroidView(
            factory = {
                PreviewView(it).apply {
                    this.controller = controller
                    controller.bindToLifecycle(lifecycleOwner)
                    this.scaleType = PreviewView.ScaleType.FILL_CENTER
                }
            }, modifier = modifier
        )
    }

    /**
     * Takes a photo using the given controller.
     *
     * @param controller The LifecycleCameraController to control the camera.
     * @param context The context to use for executing the photo capture.
     * @param onPhotoTaken A callback function to handle the captured photo as an ImageBitmap.
     */
    fun takePhoto(
        controller: LifecycleCameraController, context: Context, onPhotoTaken: (ImageBitmap) -> Unit
    ) {
        controller.takePicture(ContextCompat.getMainExecutor(context),
            object : ImageCapture.OnImageCapturedCallback() {
                override fun onCaptureSuccess(image: ImageProxy) {
                    val matrix = Matrix().apply {
                        postRotate(image.imageInfo.rotationDegrees.toFloat())
                    }

                    val formattedBitmap = Bitmap.createBitmap(
                        image.toBitmap(),
                        0,
                        0,
                        image.width,
                        image.height,
                        matrix,
                        true
                    )

                    onPhotoTaken(formattedBitmap.asImageBitmap())
                }

                override fun onError(exception: ImageCaptureException) {
                    Log.e("Camera", "Couldn't take photo: ", exception)
                }
            }
        )
    }
}