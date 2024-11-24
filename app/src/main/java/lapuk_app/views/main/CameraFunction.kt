package lapuk_app.views.main

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.util.Base64.DEFAULT
import android.util.Log
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.ByteArrayOutputStream
import java.io.IOException
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi


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
    controller: LifecycleCameraController, context: Context, onPhotoTaken: (Bitmap) -> Unit
) {
    controller.takePicture(
        ContextCompat.getMainExecutor(context),
        object : ImageCapture.OnImageCapturedCallback() {
            override fun onCaptureSuccess(image: ImageProxy) {
                val matrix = Matrix().apply {
                    postRotate(image.imageInfo.rotationDegrees.toFloat())
                }

                val formattedBitmap = Bitmap.createBitmap(
                    image.toBitmap(), 0, 0, image.width, image.height, matrix, true
                )

                onPhotoTaken(formattedBitmap)

                image.close()
            }

            override fun onError(exception: ImageCaptureException) {
                Log.e("Camera", "Couldn't take photo: ", exception)
            }
        })
}

/**
 * Sends a request to analyze the given Base64 encoded string.
 *
 * @param encodedString The Base64 encoded string to be analyzed.
 * @return The result of the analysis as a string.
 */
fun requestAnalysis(encodedString: String, callback: (String) -> Unit) {
    val request = Request.Builder().url("http://10.8.130.186:5000/detect").post(
        encodedString.toRequestBody("text/plain".toMediaTypeOrNull())
    ).build()

    Log.d("Request", "Sending request to analyze image.")

    OkHttpClient().newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            Log.e("Request", "Request failed: $e")
        }

        override fun onResponse(call: Call, response: Response) {
            if (response.isSuccessful) {
                Log.d("Request", "Request successful.")

                Thread.sleep(100) // artificial delay to stop errors in emulator

                val result = response.body?.string() ?: "Error: No response received."

                Log.d("Request", "Result received: $result.")

                callback(result)
            } else {
                Log.e("Request", "Request failed: ${response.code}")
            }
        }
    })
}

/**
 * Encodes a given Bitmap image to a Base64 string.
 *
 * @param image The Bitmap image to encode.
 * @return The Base64 encoded string of the image.
 */
@OptIn(ExperimentalEncodingApi::class)
fun encodeBitmap(image: Bitmap): String {
    val byteArrayOutputStream = ByteArrayOutputStream()
    image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
    val byteArray = byteArrayOutputStream.toByteArray()
    return Base64.encode(byteArray)
}

/**
 * Decodes a given Base64 encoded string to a Bitmap image.
 *
 * @param encodedString The Base64 encoded string to decode.
 * @return The decoded Bitmap image.
 */
@OptIn(ExperimentalEncodingApi::class)
fun decodeToBitmap(encodedString: String): Bitmap {
    val byteArray = Base64.decode(encodedString, DEFAULT)
    return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
}

