package lapuk_app.views.main.ui.pages

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle.Companion.Italic
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import lapuk_app.views.main.repository.HeatmapRepository
import lapuk_app.views.main.ui.theme.Typography
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Preview(
    showBackground = true, device = "spec:width=1080px,height=2400px,dpi=440,navigation=buttons"
)
@Composable
fun HeatmapsPage() {
    //column
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 86.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        /* title */
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier,
                text = "HEAT MAP",
                style = Typography.titleMedium
            )
        }

        Spacer(modifier = Modifier.height(28.dp))

        /* description */
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier
                    .padding(horizontal = 36.dp),
                text = "The heatmap below represents population density for areas near local landfills registered around the region:",
                textAlign = TextAlign.Center,
                style = Typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.height(28.dp))

        /* description */
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier
                    .padding(horizontal = 36.dp),
                text = "Dataset is sourced from philatlas.com",
                style = Typography.bodyMedium.copy(fontStyle = Italic),
                textAlign = TextAlign.Center,
            )
        }

        Spacer(modifier = Modifier.height(36.dp))

        // Heatmap View
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            contentAlignment = Alignment.Center
        ) {
            HeatmapScreen()
        }
    }
}

@Composable
fun HeatmapScreen() {
    val context = LocalContext.current // Provides the current Android context
    var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }
    val repository = HeatmapRepository()

    LaunchedEffect(Unit) {
        fetchHeatmap(context = context, repository = repository) { bitmap ->
            imageBitmap = bitmap
        }
    }

    imageBitmap?.let {
        Image(bitmap = it, contentDescription = "Heatmap")
    } ?: run {
        CircularProgressIndicator()
    }
}


fun fetchHeatmap(
    context: Context,
    repository: HeatmapRepository,
    onSuccess: (ImageBitmap) -> Unit
) {
    repository.getHeatmap().enqueue(object : Callback<ResponseBody> {
        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
            if (response.isSuccessful) {
                // Log the response to confirm the image is received
                Log.d("API Response", "Image received, size: ${response.body()?.contentLength()} bytes")

                // Get the image as a byte stream and decode it
                val inputStream = response.body()?.byteStream()
                val bitmap = BitmapFactory.decodeStream(inputStream)
                if (bitmap != null) {
                    onSuccess(bitmap.asImageBitmap()) // Return the bitmap to the UI
                } else {
                    Log.e("API Error", "Failed to decode the image")
                }
            } else {
                Log.e("API Error", "Failed to fetch heatmap: ${response.code()}")
            }
        }

        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            // Handle network failure
            Log.e("Network Error", "Error fetching heatmap image", t)
        }
    })
}

