package com.seif.foody.bindingadapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.seif.foody.adapters.FavouriteRecipeAdapter
import com.seif.foody.data.database.entities.FavouriteEntity

class FavouriteRecipesBinding {
    companion object {
        // setVisibility refers to favouriteRecipe
        // setData refers to myAdapter

        @BindingAdapter("setVisibility", "setData", requireAll = false)
        @JvmStatic
        fun setVisibility(
            view: View,
            favouriteRecipe: List<FavouriteEntity>?,
            myAdapter: FavouriteRecipeAdapter?
        ) {
            when (view) {
                is RecyclerView -> {
                    val dataCheck = favouriteRecipe.isNullOrEmpty()
                    view.isInvisible = dataCheck
                    if (!dataCheck) {
                        favouriteRecipe?.let {
                            myAdapter?.addFavouriteRecipes(it)
                        }
                    }
                }
                else -> view.isVisible = favouriteRecipe.isNullOrEmpty()
            }
        }
    }
}