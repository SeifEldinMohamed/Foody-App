package com.seif.foody.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.seif.foody.databinding.RecipeItemRowBinding
import com.seif.foody.models.FoodRecipe
import com.seif.foody.models.Result
import com.seif.foody.utils.RecipeDiffUtil

class RecipesAdapter : RecyclerView.Adapter<RecipesAdapter.MyViewHolder>() {
    var recipes = emptyList<Result>()
    class MyViewHolder(private val binding: RecipeItemRowBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(recipeResult: Result){
            binding.result = recipeResult
            binding.executePendingBindings() // Evaluates the pending bindings, updating any Views that have expressions bound to modified variables. This must be run on the UI thread.

        }
        companion object{
            fun from(parent: ViewGroup): MyViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RecipeItemRowBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(recipes[position])
    }

    override fun getItemCount(): Int {
       return recipes.size
    }

   fun setData(newData: FoodRecipe){
       val recipeDiffUtil = RecipeDiffUtil(this.recipes, newData.results)
       val diffUtilResult = DiffUtil.calculateDiff(recipeDiffUtil)
       this.recipes = newData.results
       diffUtilResult.dispatchUpdatesTo(this)

   }
}