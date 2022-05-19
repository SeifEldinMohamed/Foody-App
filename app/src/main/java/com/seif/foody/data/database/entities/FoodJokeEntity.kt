package com.seif.foody.data.database.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.seif.foody.models.FoodJoke
import com.seif.foody.utils.Constants.Companion.FOOD_JOKE_TABLE

@Entity(tableName = FOOD_JOKE_TABLE)
class FoodJokeEntity(
    @Embedded // will inspect our foodJoke model class and it will take it's field and store that in new table
    var foodJoke: FoodJoke
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}