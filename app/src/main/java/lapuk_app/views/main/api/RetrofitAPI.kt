package lapuk_app.views.main.api

import retrofit2.Call
import retrofit2.http.GET
import okhttp3.ResponseBody

interface ApiService {
    @GET("render-heatmap") // Endpoint of your Flask API
    fun getHeatmap(): Call<ResponseBody>
}
