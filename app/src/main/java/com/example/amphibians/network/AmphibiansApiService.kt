package com.example.amphibians.network

import com.example.amphibians.model.Amphibian
import retrofit2.http.GET

// Сэрверт хүсэлт өгөх Interface
interface AmphibiansApiService {
    @GET("amphibians")
    suspend fun getAmphibians(): List<Amphibian>
}