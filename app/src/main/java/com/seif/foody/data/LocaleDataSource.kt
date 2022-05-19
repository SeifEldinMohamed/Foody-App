package com.seif.foody.data

import com.seif.foody.data.database.RecipesDao
import com.seif.foody.data.database.entities.FavouriteEntity
import com.seif.foody.data.database.entities.FoodJokeEntity
import com.seif.foody.data.database.entities.RecipesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocaleDataSource @Inject constructor(
    private val recipesDao: RecipesDao
) {

    fun readRecipes(): Flow<List<RecipesEntity>> {
        return recipesDao.readRecipes()
    }

    fun readFavouriteRecipes(): Flow<List<FavouriteEntity>> {
        return recipesDao.readFavouriteRecipes()
    }

    fun readFoodJoke(): Flow<List<FoodJokeEntity>> {
        return recipesDao.readFoodJoke()
    }

    suspend fun insertRecipes(recipesEntity: RecipesEntity) {
        recipesDao.insertRecipes(recipesEntity)
    }

    suspend fun insertFavouriteRecipe(favouriteEntity: FavouriteEntity) {
        recipesDao.insertFavouriteRecipe(favouriteEntity)
    }

    suspend fun insertFoodJoke(foodJokeEntity: FoodJokeEntity) {
        recipesDao.insertFoodJoke(foodJokeEntity)
    }

    suspend fun deleteFavouriteRecipe(favouriteEntity: FavouriteEntity) {
        recipesDao.deleteFavouriteRecipe(favouriteEntity)
    }

    suspend fun deleteAllFavouriteRecipes() {
        recipesDao.deleteAllFavouriteRecipes()
    }
}