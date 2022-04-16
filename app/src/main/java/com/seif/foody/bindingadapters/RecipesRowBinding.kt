package com.seif.foody.bindingadapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.seif.foody.R

class RecipesRowBinding {
    companion object {
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
    }
}