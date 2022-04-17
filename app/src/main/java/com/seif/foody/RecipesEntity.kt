package com.seif.foody

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.seif.foody.models.FoodRecipe
import com.seif.foody.utils.Constants.Companion.RECIPES_TABLE

@Entity(tableName = RECIPES_TABLE)
class RecipesEntity(
   var foodRecipe: FoodRecipe
) {
    @PrimaryKey(autoGenerate = false)
    var id :Int = 0
}