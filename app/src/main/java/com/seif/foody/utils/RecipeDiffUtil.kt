package com.seif.foody.utils

import androidx.recyclerview.widget.DiffUtil
import com.seif.foody.models.Result

class RecipeDiffUtil<T>( // updated diff util to use generics to be apple to use this class with Result and ExtendedIngredient classes
    private val oldList: List<T>,
    private val newList: List<T>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
       return oldList[oldItemPosition] === newList[newItemPosition] // check if they are identical
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
       return oldList[oldItemPosition] == newList[newItemPosition]
    }
}