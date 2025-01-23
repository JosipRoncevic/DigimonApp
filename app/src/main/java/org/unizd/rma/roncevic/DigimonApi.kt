package org.unizd.rma.roncevic

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DigimonApi {
    @GET("digimon")
    suspend fun getAllDigimons():retrofit2.Response<List<Digimon>>

    @GET("digimon/name/{name}")
    suspend fun getDigimonByName(@Path("name")name:String): Response<List<Digimon>>

    @GET("digimon/level/{level}")
    suspend fun getDigimonByLevel(@Path("level")level:String): Response<List<Digimon>>
}