package com.seif.foody.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.seif.foody.data.DataStoreRepository
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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(
    application: Application,
    private val dataStoreRepository: DataStoreRepository
) : AndroidViewModel(application) {

    private var mealType  = DEFAULT_MEAL_TYPE
    private var dietType  = DEFAULT_DIET_TYPE

    val readMealAndDietType = dataStoreRepository.readMealAndDietType

    fun saveMealTypeAndType(
        mealType: String,
        mealTypeId: Int,
        dietType: String,
        dietTypeId: Int
    ) = viewModelScope.launch(Dispatchers.IO) {
        dataStoreRepository.saveMealTypeAndDietType(mealType, mealTypeId, dietType, dietTypeId)
    }

    fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()

        viewModelScope.launch(Dispatchers.IO) {
            readMealAndDietType.collect{ value -> // Accepts the given collector and emits values into it.
                mealType = value.selectedMealType
                dietType = value.selectedDietType
            }
        }

        queries[QUERY_NUMBER] =
            DEFAULT_RECIPES_NUMBER // number of recipes to get from our request [1..100]
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_TYPE] = mealType
        queries[QUERY_DIET] = dietType
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"
        return queries
    }
}