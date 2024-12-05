package lapuk_app.views.main.repository

import lapuk_app.views.main.api.ApiService
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HeatmapRepository {

    private val apiService: ApiService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://127.0.0.1:5000/") // Replace with your backend URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)
    }

    fun getHeatmap(): Call<ResponseBody> {
        return apiService.getHeatmap()
    }
}