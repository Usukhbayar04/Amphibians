package com.example.amphibians.data

import com.example.amphibians.network.AmphibiansApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

// Программын бусад хэсгүүдэд хамаарал олгох
interface AppContainer {
    val amphibiansRepository: AmphibiansRepository
}

// AppContainer interface-ийн хэрэгжүүлэн, тодорхой хэрэгжүүлэлтийг хангадаг
class DefaultAppContainer : AppContainer {
    private val BASE_URL = "https://android-kotlin-fun-mars-server.appspot.com/"

    // kotlinx.serialization use converter Retrofit.Builder объектийг үүсгэнэ.
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BASE_URL)
        .build()

    //Retrofit service object for creating api calls
    private val retrofitService: AmphibiansApiService by lazy {
        retrofit.create(AmphibiansApiService::class.java)
    }

    // DI, repository-ийн хэрэгжилт
    override val amphibiansRepository: AmphibiansRepository by lazy {
        DefaultAmphibiansRepository(retrofitService)
    }
}
