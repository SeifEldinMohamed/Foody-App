package com.seif.foody.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.seif.foody.utils.Constants.Companion.API_KEY
import com.seif.foody.utils.Constants.Companion.DEFAULT_DIET_TYPE
import com.seif.foody.utils.Constants.Companion.DEFAULT_MEAL_TYPE
import com.seif.foody.utils.Constants.Companion.DEFAULT_RECIPES_NUMBER
import com.seif.foody.utils.Constants.Companion.QUERY_ADD_RECIPE_INFORMATION
import com.seif.foody.utils.Constants.Companion.QUERY_API_KEY
import com.seif.foody.utils.Constants.Companion.QUERY_DIET
import com.seif.foody.utils.Constants.Companion.QUERY_FILL_INGREDIENTS
import com.seif.foody.utils.Constants.Companion.QUERY_NUMBER
import com.seif.foody.utils.Constants.Companion.QUERY_TYPE

class RecipesViewModel(application:Application) : AndroidViewModel(application) {

     fun applyQueries():HashMap<String, String>{
        val queries : HashMap<String, String> = HashMap()
        queries[QUERY_NUMBER] = DEFAULT_RECIPES_NUMBER // number of recipes to get from our request [1..100]
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_TYPE] = DEFAULT_MEAL_TYPE // we will get value dynamically
        queries[QUERY_DIET] = DEFAULT_DIET_TYPE
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"
        return queries
    }
}