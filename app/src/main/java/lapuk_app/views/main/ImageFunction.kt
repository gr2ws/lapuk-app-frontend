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
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.concurrent.TimeUnit
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
 * Data class representing the results received from the analysis.
 *
 * @property image The Base64 encoded image string.
 * @property detections The list of detections, each represented as a list of any type.
 */
data class ResultsReceived(
    val image: String, val detections: List<List<Any>>
)

/**
 * Data class representing the properly reformatted analysis results.
 *
 * @property image The Base64 encoded image string.
 * @property detections The list of detections, each represented as a pair of string and float.
 */
data class AnalysisResults(
    val image: String, val detections: List<Pair<String, Float>>
)

/**
 * Sends a request to analyze the given Base64 encoded string.
 *
 * @param encodedString The Base64 encoded string to be analyzed.
 * @param callback The callback function to handle the analysis results.
 */
fun requestAnalysis(encodedString: String, callback: (AnalysisResults) -> Unit) {
    val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    val request = Request.Builder()
        .url("https://lapuk-app-backend-1d8f0518796c.herokuapp.com/detect")
        .post(encodedString.toRequestBody("text/plain".toMediaTypeOrNull()))
        .build()

    Log.e("Request", "Sending request to analyze image.")

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            Log.e("Request", "Request failed: $e")
        }

        override fun onResponse(call: Call, response: Response) {
            if (response.isSuccessful) {
                Log.d("Request", "Request successful.")

                Thread.sleep(100) // artificial delay to stop errors in emulator

                val responseReceived = response.body?.string() ?: "Error: No response received."
                val analysisReceived = Gson().fromJson(responseReceived, ResultsReceived::class.java)
                val mappedPairs = analysisReceived.detections.map { it.toTuple() }

                val analysisResults = AnalysisResults(analysisReceived.image, mappedPairs)

                Log.d("Request", "Result received: $responseReceived, ${analysisResults.detections}")

                callback(analysisResults)
            } else {
                Log.e("Request", "Request failed: ${response.code}")
            }
        }
    })
}


/**
 * Converts a list of any type to a pair of string and float.
 *
 * @return A pair where the first element is a string and the second element is a float.
 * @throws IllegalArgumentException if the list size is not 2.
 */
fun List<Any>.toTuple(): Pair<String, Float> {
    if (this.size != 2) throw IllegalArgumentException("List size is not 2")
    return Pair(this[0].toString(), this[1].toString().toFloat())
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