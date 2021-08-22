package dev.demo.mvvmtesting.repository

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class CocktailService {
    companion object {
        private const val apiKeyTest = "1"
        private const val apiUrl = "https://www.thecocktaildb.com/api/json/v1/$apiKeyTest/"

        val client: Retrofit get() =
            Retrofit.Builder()
                .baseUrl(apiUrl)
                .addConverterFactory(GsonConverterFactory.create())
//                .client(client)
                .build()

    }
}