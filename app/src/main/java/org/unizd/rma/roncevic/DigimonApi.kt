package org.unizd.rma.roncevic

import retrofit2.http.GET

interface DigimonApi {
    @GET("digimon")
    suspend fun getAllDigimons():retrofit2.Response<List<Digimon>>
}