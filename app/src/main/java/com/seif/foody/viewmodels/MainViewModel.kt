package com.seif.foody.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.*
import com.seif.foody.data.Repository
import com.seif.foody.data.database.entities.FavouriteEntity
import com.seif.foody.data.database.entities.FoodJokeEntity
import com.seif.foody.data.database.entities.RecipesEntity
import com.seif.foody.models.FoodJoke
import com.seif.foody.models.FoodRecipe
import com.seif.foody.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) { // since we will need a application reference so we will use AndroidViewModel

    /** ROOM DATABASE**/

    val readRecipes: LiveData<List<RecipesEntity>> = repository.locale.readRecipes().asLiveData()
    val readFavouriteRecipes: LiveData<List<FavouriteEntity>> =
        repository.locale.readFavouriteRecipes().asLiveData()
    val readFoodJoke: LiveData<List<FoodJokeEntity>> = repository.locale.readFoodJoke().asLiveData()

     private fun insertRecipes(recipesEntity: RecipesEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.locale.insertRecipes(recipesEntity)
        }
    }

    fun insertFavouriteRecipe(favouriteEntity: FavouriteEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.locale.insertFavouriteRecipe(favouriteEntity)
        }
    }

    fun insertFoodJoke(foodJokeEntity: FoodJokeEntity){
        viewModelScope.launch(Dispatchers.IO) {
            repository.locale.insertFoodJoke(foodJokeEntity)
        }
    }

    fun deleteFavouriteRecipe(favouriteEntity: FavouriteEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.locale.deleteFavouriteRecipe(favouriteEntity)
        }
    }

    fun deleteAllFavouriteRecipes() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.locale.deleteAllFavouriteRecipes()
        }
    }

    /** RETROFIT **/
    var recipesResponse: MutableLiveData<NetworkResult<FoodRecipe>> = MutableLiveData()
    var searchedRecipesResponse: MutableLiveData<NetworkResult<FoodRecipe>> = MutableLiveData()

    // food joke
    var foodJokeResponse: MutableLiveData<NetworkResult<FoodJoke>> = MutableLiveData()
    fun searchRecipes(searchQuery: Map<String, String>) = viewModelScope.launch {
        searchRecipesSafeCall(searchQuery)
    }


    fun getRecipes(queries: Map<String, String>) = viewModelScope.launch {
        getRecipesSafeCall(queries)
    }

    fun getFoodJoke(apiKey:String) = viewModelScope.launch {
        getFoodJokeSafeCall(apiKey)
    }

    private suspend fun getRecipesSafeCall(queries: Map<String, String>) {
        recipesResponse.value =
            NetworkResult.Loading() // first case while trying to connect with api until we gor a response.
        if (hasInternetConnection()) { // if we have internet connection then we want to make get request to our api and store the result inside recipesResult Mutable live data object
            try {
                val response = repository.remote.getRecipes(queries)
                recipesResponse.value = handleFoodRecipesResponse(response)

                // cashing data
                val foodRecipe = recipesResponse.value!!.data
                if (foodRecipe != null) {
                    offlineCacheRecipes(foodRecipe)
                }
            } catch (e: Exception) {
                recipesResponse.value = NetworkResult.Error("Recipes Not Found")

            }
        } else { // error happened
            recipesResponse.value = NetworkResult.Error("No Internet Connection")
        }
    }


    private suspend fun searchRecipesSafeCall(searchQuery: Map<String, String>) {
        searchedRecipesResponse.value =
            NetworkResult.Loading() // first case while trying to connect with api until we gor a response.
        if (hasInternetConnection()) { // if we have internet connection then we want to make get request to our api and store the result inside recipesResult Mutable live data object
            try {
                val response = repository.remote.searchRecipes(searchQuery)
                searchedRecipesResponse.value = handleFoodRecipesResponse(response)
            } catch (e: Exception) {
                searchedRecipesResponse.value = NetworkResult.Error("Recipes Not Found")
            }
        } else { // error happened
            searchedRecipesResponse.value = NetworkResult.Error("No Internet Connection")
        }
    }
   private suspend fun getFoodJokeSafeCall(apiKey: String) {
        foodJokeResponse.value =
            NetworkResult.Loading() // first case while trying to connect with api until we gor a response.
        if (hasInternetConnection()) { // if we have internet connection then we want to make get request to our api and store the result inside recipesResult Mutable live data object
            try {
                val response = repository.remote.getFoodJoke(apiKey)
                foodJokeResponse.value = handleFoodJokeResponse(response)

                val foodJoke = foodJokeResponse.value!!.data
                if (foodJoke!=null){
                    offlineCacheFoodJoke(foodJoke)
                }
            } catch (e: Exception) {
                foodJokeResponse.value = NetworkResult.Error("Recipes Not Found")
            }
        } else { // error happened
            foodJokeResponse.value = NetworkResult.Error("No Internet Connection")
        }
    }

    private fun offlineCacheRecipes(foodRecipe: FoodRecipe) {
        val recipesEntity = RecipesEntity(foodRecipe)
        insertRecipes(recipesEntity)
    }
    private fun offlineCacheFoodJoke(foodJoke: FoodJoke) {
        val foodJokeEntity = FoodJokeEntity(foodJoke)
        insertFoodJoke(foodJokeEntity)
    }

    private fun handleFoodRecipesResponse(response: Response<FoodRecipe>): NetworkResult<FoodRecipe> {
        return when {
            response.message().toString().contains("timeout") -> NetworkResult.Error("Timeout")
            response.code() == 402 -> NetworkResult.Error("Api Key Limited.")
            response.body()!!.results.isNullOrEmpty() -> NetworkResult.Error("Recipes Not Found.")
            response.isSuccessful -> { // we will return food recipes from api
                val foodRecipes = response.body()
                NetworkResult.Success(foodRecipes)
            }
            else -> NetworkResult.Error(response.message())
        }
    }
    private fun handleFoodJokeResponse(response: Response<FoodJoke>): NetworkResult<FoodJoke> {
        return when {
            response.message().toString().contains("timeout") -> NetworkResult.Error("Timeout")
            response.code() == 402 -> NetworkResult.Error("Api Key Limited.")
            response.isSuccessful -> { // we will return food recipes from api
                val foodJoke = response.body()
                NetworkResult.Success(foodJoke)
            }
            else -> NetworkResult.Error(response.message())
        }
    }

    // function to check internet connectivity
    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when { // return true if there is an internet connection from wifi, cellular and ethernet
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}
/** Note:
 * recipesResponse.value : used in main thread only
 * recipesResponse.postValue(): used in background thread
 * **/