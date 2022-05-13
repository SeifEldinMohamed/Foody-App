package com.seif.foody.bindingadapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.seif.foody.adapters.FavouriteRecipeAdapter
import com.seif.foody.data.database.entities.FavouriteEntity

class FavouriteRecipesBinding {
    companion object {
        // viewVisibility refers to favouriteRecipe
        // setData refers to myAdapter

        @BindingAdapter("viewVisibility","setData", requireAll = false)
        @JvmStatic
        fun setDataAndViewVisibility(
            view: View,
            favouriteRecipe: List<FavouriteEntity>?,
            myAdapter: FavouriteRecipeAdapter?
        ) {
            if(favouriteRecipe.isNullOrEmpty()){ // no favourite recipes
                when(view){
                    is TextView -> {
                        view.visibility = View.VISIBLE
                    }
                    is ImageView -> {
                        view.visibility = View.VISIBLE
                    }
                    is RecyclerView -> {
                        view.visibility = View.INVISIBLE
                    }
                }
            }
            else{
                when(view){
                    is TextView -> {
                        view.visibility = View.INVISIBLE
                    }
                    is ImageView -> {
                        view.visibility = View.INVISIBLE
                    }
                    is RecyclerView -> {
                        view.visibility = View.VISIBLE
                        myAdapter?.addFavouriteRecipes(favouriteRecipe)
                    }
                }
            }
        }
    }
}