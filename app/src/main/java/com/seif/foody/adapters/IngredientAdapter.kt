package com.seif.foody.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.seif.foody.R
import com.seif.foody.databinding.IngredientRowItemBinding
import com.seif.foody.models.ExtendedIngredient
import com.seif.foody.utils.Constants.Companion.BASE_IMAGE_URL
import com.seif.foody.utils.RecipeDiffUtil
import java.util.*

class IngredientAdapter : RecyclerView.Adapter<IngredientAdapter.MyViewHolder>() {

    private var ingredientList = emptyList<ExtendedIngredient>()

    class MyViewHolder(private val binding: IngredientRowItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(ingredient: ExtendedIngredient) {
            binding.txtIngredientName.text = ingredient.name.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(
                    Locale.getDefault()
                ) else it.toString()
            }
            binding.txtIngredientAmount.text = ingredient.amount.toString()
            binding.txtIngredientUnit.text = ingredient.unit
            binding.txtIngredientConsistency.text = ingredient.consistency
            binding.txtIngredientOriginal.text = ingredient.original
            binding.ingredientImage.load(BASE_IMAGE_URL + ingredient.image){
                crossfade(600)
                error(R.drawable.ic_error_placeholder)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            IngredientRowItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(ingredientList[position])
    }

    override fun getItemCount(): Int {
        return ingredientList.size
    }

    fun addIngredientList(ingredients: List<ExtendedIngredient>) {
        val ingredientDiffUtil = RecipeDiffUtil(this.ingredientList, ingredients)
        val diffUtilResult = DiffUtil.calculateDiff(ingredientDiffUtil)
        this.ingredientList = ingredients
        diffUtilResult.dispatchUpdatesTo(this)
    }
}