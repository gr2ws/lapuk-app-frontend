package lapuk_app.views.main.ui.pages

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import lapuk_app.views.main.repository.HeatmapRepository
import lapuk_app.views.main.ui.theme.Typography
import lapuk_app.views.main.ui.theme.br4
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

        Spacer(modifier = Modifier.height(64.dp))

        //TableauView
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 48.dp)
                .size(264.dp, 264.dp)
                .background(br4)
        ) {
            HeatmapScreen()
        }
    }
}

@Composable
fun HeatmapScreen() {
    var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }
    val repository = HeatmapRepository()

    LaunchedEffect(Unit) {
        /*fetchHeatmap(repository = repository) { bitmap ->
            imageBitmap = bitmap
        }*/
    }

    imageBitmap?.let {
        Image(bitmap = it, contentDescription = "Heatmap")
    } ?: run {
        // Show loading spinner if imageBitmap is not available yet
        CircularProgressIndicator()
    }
}

@Composable
fun fetchHeatmap(repository: HeatmapRepository, onSuccess: (ImageBitmap) -> Unit) {
    repository.getHeatmap().enqueue(object : Callback<ResponseBody> {
        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
            if (response.isSuccessful) {
                val inputStream = response.body()?.byteStream()
                val bitmap = BitmapFactory.decodeStream(inputStream)
                onSuccess(bitmap.asImageBitmap())
            }
        }

        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            // Handle failure (e.g., show error message)
        }
    })
}