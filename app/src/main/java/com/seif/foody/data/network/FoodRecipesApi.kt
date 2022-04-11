package com.seif.foody.data.network

import com.seif.foody.models.FoodRecipe
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface FoodRecipesApi {
    @GET("/recipes/complexSearch") // end point
    suspend fun getRecipes(
        @QueryMap recipes: Map<String, String> // Specifies whether parameter names and values are already URL encoded
    ): Response<FoodRecipe>
}