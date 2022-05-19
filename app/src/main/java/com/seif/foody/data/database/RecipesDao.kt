package com.seif.foody.data.database

import androidx.room.*
import com.seif.foody.data.database.entities.FavouriteEntity
import com.seif.foody.data.database.entities.FoodJokeEntity
import com.seif.foody.data.database.entities.RecipesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipesEntity: RecipesEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavouriteRecipe(favouriteEntity: FavouriteEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFoodJoke(foodJokeEntity: FoodJokeEntity)

    @Query("SELECT * FROM recipes_table ORDER BY id ASC")
    fun readRecipes(): Flow<List<RecipesEntity>>

    @Query("SELECT * FROM FAVOURITE_RECIPES_TABLE ORDER BY id ASC")
    fun readFavouriteRecipes(): Flow<List<FavouriteEntity>>

    @Query("SELECT * FROM food_joke_table ORDER BY id ASC")
    fun readFoodJoke(): Flow<List<FoodJokeEntity>>
    @Delete
    suspend fun deleteFavouriteRecipe(favouriteEntity: FavouriteEntity)

    @Query("DELETE FROM FAVOURITE_RECIPES_TABLE")
    suspend fun deleteAllFavouriteRecipes()
}