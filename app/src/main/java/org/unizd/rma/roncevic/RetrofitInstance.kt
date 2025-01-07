package org.unizd.rma.roncevic

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val BASE_URL = "https://digimon-api.vercel.app/api/"

    private fun getInstance(): Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    val digimonApi: DigimonApi = getInstance().create(DigimonApi::class.java)
}