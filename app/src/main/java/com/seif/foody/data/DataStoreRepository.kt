package com.seif.foody.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.seif.foody.utils.Constants.Companion.DEFAULT_DIET_TYPE
import com.seif.foody.utils.Constants.Companion.DEFAULT_MEAL_TYPE
import com.seif.foody.utils.Constants.Companion.PREFERENCES_BACK_ONLINE
import com.seif.foody.utils.Constants.Companion.PREFERENCES_DIET_TYPE
import com.seif.foody.utils.Constants.Companion.PREFERENCES_DIET_TYPE_ID
import com.seif.foody.utils.Constants.Companion.PREFERENCES_MEAL_TYPE
import com.seif.foody.utils.Constants.Companion.PREFERENCES_MEAL_TYPE_ID
import com.seif.foody.utils.Constants.Companion.PREFERENCES_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

@ActivityRetainedScoped // we used this bec this class will be used inside RecipesViewModel
class DataStoreRepository @Inject constructor(@ApplicationContext private val context: Context) {

    companion object {
        private object PreferenceKeys {
            val selectedMealType = stringPreferencesKey(PREFERENCES_MEAL_TYPE)
            val selectedMealTypeId = intPreferencesKey(PREFERENCES_MEAL_TYPE_ID)
            val selectedDietType = stringPreferencesKey(PREFERENCES_DIET_TYPE)
            val selectedDietTypeId = intPreferencesKey(PREFERENCES_DIET_TYPE_ID)
            val backOnline = booleanPreferencesKey(PREFERENCES_BACK_ONLINE)
        }
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = PREFERENCES_NAME
    )

    suspend fun saveBackOnline(backOnline: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferenceKeys.backOnline] = backOnline
        }
    }

    suspend fun saveMealTypeAndDietType(
        mealType: String,
        mealTypeId: Int,
        dietType: String,
        dietTypeId: Int
    ) {
        context.dataStore.edit { preferences -> // of type mutable preferences
            preferences[PreferenceKeys.selectedMealType] = mealType
            preferences[PreferenceKeys.selectedMealTypeId] = mealTypeId
            preferences[PreferenceKeys.selectedDietType] = dietType
            preferences[PreferenceKeys.selectedDietTypeId] = dietTypeId
        }
    }


    val readMealAndDietType: Flow<MealAndDietType> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences -> // Returns a flow containing the results of applying the given transform function to each value of the original flow.
            val selectedMealType = preferences[PreferenceKeys.selectedMealType] ?: DEFAULT_MEAL_TYPE
            val selectedMealTypeId = preferences[PreferenceKeys.selectedMealTypeId] ?: 0
            val selectedDietType = preferences[PreferenceKeys.selectedDietType] ?: DEFAULT_DIET_TYPE
            val selectedDietTypeId = preferences[PreferenceKeys.selectedDietTypeId] ?: 0
            MealAndDietType(
                selectedMealType,
                selectedMealTypeId,
                selectedDietType,
                selectedDietTypeId
            )
        }

    val readBackOnline: Flow<Boolean> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val backOnline = preferences[PreferenceKeys.backOnline] ?: false
            backOnline
        }
}

data class MealAndDietType(
    val selectedMealType: String,
    val selectedMealTypeId: Int,
    val selectedDietType: String,
    val selectedDietTypeId: Int
)