package com.seif.foody.data.network

import com.seif.foody.models.FoodJoke
import com.seif.foody.models.FoodRecipe
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val foodRecipesApi: FoodRecipesApi // hilt will search for a function that returns the same type(FoodRecipeApi) in NetworkModule
) { // we need to inject RemoteDataSource with FoodRecipeApi

    suspend fun getRecipes(queries: Map<String, String>): Response<FoodRecipe> {
        return foodRecipesApi.getRecipes(queries)
    }

    suspend fun searchRecipes(searchQuery: Map<String, String>): Response<FoodRecipe> {
        return foodRecipesApi.searchRecipes(searchQuery)
    }

    suspend fun getFoodJoke(apiKey:String): Response<FoodJoke>{
        return foodRecipesApi.getFoodJoke(apiKey)
    }
}