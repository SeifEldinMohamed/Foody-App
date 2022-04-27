package com.seif.foody.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.preferencesDataStoreFile
import com.seif.foody.utils.Constants.Companion.PREFERENCES_DIET_TYPE
import com.seif.foody.utils.Constants.Companion.PREFERENCES_DIET_TYPE_ID
import com.seif.foody.utils.Constants.Companion.PREFERENCES_MEAL_TYPE
import com.seif.foody.utils.Constants.Companion.PREFERENCES_MEAL_TYPE_ID
import com.seif.foody.utils.Constants.Companion.PREFERENCES_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

class DataStoreRepository @Inject constructor(@ApplicationContext private val context: Context) {

    private object PreferenceKeys{
        val selectedMealType = stringPreferencesKey(PREFERENCES_MEAL_TYPE)
        val selectedMealTypeId = intPreferencesKey(PREFERENCES_MEAL_TYPE_ID)
        val selectedDietType = stringPreferencesKey(PREFERENCES_DIET_TYPE)
        val selectedDietTypeId = intPreferencesKey(PREFERENCES_DIET_TYPE_ID)
    }

    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = PREFERENCES_NAME
    )
    suspend fun saveMealTypeAndDietType(mealType:String, mealTypeId:Int, dietType:String, dietTypeId:Int){
        context.dataStore.edit { preferences -> // of type mutable preferences
            preferences[PreferenceKeys.selectedMealType] = mealType
            preferences[PreferenceKeys.selectedMealTypeId] = mealTypeId
            preferences[PreferenceKeys.selectedDietType] = dietType
            preferences[PreferenceKeys.selectedDietTypeId] = dietTypeId
        }
    }
//    val readMealAndDietType : Flow<MealAndDietType> = context.dataStore.data
//        .catch { exception ->
//
//        }

}
data class MealAndDietType(
    val selectedMealType:String,
    val selectedMealTypeId:Int,
    val selectedDietType:String,
    val selectedDietTypeId:String
)