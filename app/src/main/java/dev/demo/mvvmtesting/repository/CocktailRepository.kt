package dev.demo.mvvmtesting.repository

import dev.demo.mvvmtesting.models.DrinkResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CocktailRepository {
    @GET("search.php?")
    fun listCocktailsByName(@Query("s") s: String): Call<DrinkResponse>
}