package com.example.amphibians.data

import com.example.amphibians.model.Amphibian
import com.example.amphibians.network.AmphibiansApiService

// Amphibian-ийн мэдээллийг data source-аас авдаг.
interface AmphibiansRepository {
    // Data source-оос Amphibian-ийн list авна.
    suspend fun getAmphibians(): List<Amphibian>
}

// AmphibiansRepository хэрэгжүүлэн АmphibiansApiService ашиглан өгөгдлөө авна.
class DefaultAmphibiansRepository(private val amphibiansApiService: AmphibiansApiService ) : AmphibiansRepository {
    // AmphibiansRepository хэрэгжүүлж байгаа учир getAmphibians() дахин тодорхойлж өгнө.
    override suspend fun getAmphibians(): List<Amphibian> = amphibiansApiService.getAmphibians()
}
