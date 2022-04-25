package com.seif.foody.bindingadapters

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.ViewModelProvider
import com.seif.foody.data.database.RecipesEntity
import com.seif.foody.models.FoodRecipe
import com.seif.foody.utils.NetworkResult
import retrofit2.Response

class RecipesBinding {
    companion object{

        @BindingAdapter("readApiResponse", "readDatabase", requireAll = true)
        @JvmStatic
        fun errorImageViewVisibility(
            imageView: ImageView,
            apiResponse: NetworkResult<FoodRecipe>?,
            database: List<RecipesEntity>?
        ){
            if(apiResponse is NetworkResult.Error && database.isNullOrEmpty()){
                imageView.visibility = View.VISIBLE
                Log.d("test","1")

            }
            else if(apiResponse is NetworkResult.Loading){
                imageView.visibility = View.INVISIBLE
                Log.d("test","2")

            }
            else if(apiResponse is NetworkResult.Success){
                imageView.visibility = View.INVISIBLE
                Log.d("test","3")

            }
        }

        @BindingAdapter("readApiResponse2", "readDatabase2", requireAll = true)
        @JvmStatic
        fun errorTextViewVisibility(
            textView: TextView,
            apiResponse: NetworkResult<FoodRecipe>?,
            database: List<RecipesEntity>?
        ){
            if(apiResponse is NetworkResult.Error && database.isNullOrEmpty()){
                textView.visibility = View.VISIBLE
                textView.text = apiResponse.message.toString()
                Log.d("test","1")
            }
            else if(apiResponse is NetworkResult.Loading){
                textView.visibility = View.INVISIBLE
                Log.d("test","2")

            }
            else if(apiResponse is NetworkResult.Success){
                textView.visibility = View.INVISIBLE
                Log.d("test","3")

            }
        }
    }
}