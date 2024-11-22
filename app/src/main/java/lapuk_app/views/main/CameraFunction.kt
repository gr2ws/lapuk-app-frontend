package lapuk_app.views.main

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
                }
            },
            modifier = modifier
        )
    }
}