package lapuk_app.views.main.repository

import lapuk_app.views.main.api.ApiService
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class HeatmapRepository {

    private val apiService: ApiService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.101.45:5000/") // Ensure this matches your Flask server IP
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)
    }

    @GET("render-heatmap")
    fun getHeatmap(): Call<ResponseBody> {
        return apiService.getHeatmap()
    }
}