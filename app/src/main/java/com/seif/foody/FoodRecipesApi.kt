package com.seif.foody

import com.seif.foody.models.FoodRecipe
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface FoodRecipesApi {
    @GET("/recipes/complexSearch") // end point
    suspend fun getRecipes(
        @QueryMap recipes: Map<String, String>
    ): Response<FoodRecipe>
}