package com.seif.foody.bindingadapters

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import coil.load
import com.seif.foody.R
import com.seif.foody.models.Result
import com.seif.foody.ui.fragments.recipes.RecipeFragmentDirections
import org.jsoup.Jsoup

class RecipesRowBinding {
    companion object {

        @BindingAdapter("onRecipeClickListener")
        @JvmStatic
        fun onRecipeClickListener(recipeRowLayout: ConstraintLayout, result:Result){
            recipeRowLayout.setOnClickListener {
                try {
                    val action = RecipeFragmentDirections.actionRecipeFragmentToDetailsActivity2(result)
                    recipeRowLayout.findNavController().navigate(action)
                }
                catch (e: Exception){
                    Log.d("recipeClickListener", e.toString())
                }
            }
        }

        @BindingAdapter("loadImageFromUrl")
        @JvmStatic // to tell our compiler that this fun is static so we can access this function everywhere in this project
        fun loadImageFromUrl(imageView: ImageView, imageUrl:String){
            imageView.load(imageUrl){
                crossfade(600)
                error(R.drawable.ic_error_placeholder)
            }
        }

        @BindingAdapter("setNumberOfLikes")
        @JvmStatic // to tell our compiler that this fun is static so we can access this function everywhere in this project
        fun setNumberOfLikes(textView: TextView, likes:Int){
            textView.text = likes.toString()
        }

        @BindingAdapter("setNumberOfMinutes")
        @JvmStatic // to tell our compiler that this fun is static so we can access this function everywhere in this project
        fun setNumberOfMinutes(textView: TextView, likes:Int){
            textView.text = likes.toString()
        }

        @BindingAdapter("applyVeganColor")
        @JvmStatic // to tell our compiler that this fun is static so we can access this function everywhere in this project
        fun applyVeganColor(view: View, vegan:Boolean){
            if (vegan){
                when(view){
                    is TextView -> {
                        view.setTextColor(
                            ContextCompat.getColor(
                                view.context,
                                R.color.green
                            )
                        )
                    }
                    is ImageView -> {
                        view.setColorFilter(
                            ContextCompat.getColor(
                                view.context,
                                R.color.green
                            )
                        )
                    }
                }
            }
        }
        @BindingAdapter("parseHtml")
        @JvmStatic
        fun parseHtml(textView: TextView, description:String?){ // description can be nullable sometimes
            if (description!=null){
                val desc:String = Jsoup.parse(description).text()
                textView.text = desc
            }
        }
    }
}