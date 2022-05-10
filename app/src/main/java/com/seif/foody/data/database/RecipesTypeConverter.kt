package com.seif.foody.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.seif.foody.models.FoodRecipe
import com.seif.foody.models.Result

class RecipesTypeConverter {
    private val gson = Gson()
    @TypeConverter
    fun fromFoodRecipeToJson(foodRecipe: FoodRecipe):String{
        return gson.toJson(foodRecipe)
    }
    @TypeConverter
    fun fromJsonToFoodRecipe(data: String):FoodRecipe{
        // val listType= object : TypeToken<FoodRecipe>(){}.type // replace FoodRecipe::class.java
        return gson.fromJson(data, FoodRecipe::class.java)
    }
    @TypeConverter
    fun fromResultToJson(result: Result):String{
        return gson.toJson(result)
    }
    @TypeConverter
    fun fromJsonToResult(data: String):Result{
        // val listType= object : TypeToken<FoodRecipe>(){}.type // replace FoodRecipe::class.java
        return gson.fromJson(data, Result::class.java)
    }
}