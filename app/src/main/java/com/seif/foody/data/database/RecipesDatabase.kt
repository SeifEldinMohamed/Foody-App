package com.seif.foody.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.seif.foody.data.database.entities.FavouriteEntity
import com.seif.foody.data.database.entities.FoodJokeEntity
import com.seif.foody.data.database.entities.RecipesEntity

@Database(
    entities = [RecipesEntity::class, FavouriteEntity::class, FoodJokeEntity::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(RecipesTypeConverter::class)
abstract class RecipesDatabase: RoomDatabase() {

    abstract fun recipesDao(): RecipesDao

} // we will make our database builder in di package