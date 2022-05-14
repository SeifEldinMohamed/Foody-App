package com.seif.foody.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.seif.foody.R
import com.seif.foody.data.database.entities.FavouriteEntity
import com.seif.foody.databinding.FavouriteRecipeRowLayoutBinding
import com.seif.foody.ui.fragments.favourites.FavouriteRecipeFragmentDirections
import com.seif.foody.utils.RecipeDiffUtil

class FavouriteRecipeAdapter: RecyclerView.Adapter<FavouriteRecipeAdapter.MyViewHolder>() {

    private var favouriteRecipes = emptyList<FavouriteEntity>()
    class MyViewHolder(private val binding:FavouriteRecipeRowLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(favouriteRecipe: FavouriteEntity){
            binding.favouriteRecipe = favouriteRecipe
            binding.executePendingBindings() // to update ui when changed
            binding.favouriteRowCardView.setOnClickListener {
                val action = FavouriteRecipeFragmentDirections.actionFavouriteRecipeFragmentToDetailsActivity(favouriteRecipe.result)
                it.findNavController().navigate(action)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            FavouriteRecipeRowLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(favouriteRecipes[position])
    }

    override fun getItemCount(): Int {
        return favouriteRecipes.size
    }
    fun addFavouriteRecipes(favouriteRecipes: List<FavouriteEntity>){
        val diffUtilCallBack = RecipeDiffUtil(this.favouriteRecipes, favouriteRecipes)
        val diffUtilResult = DiffUtil.calculateDiff(diffUtilCallBack)
        this.favouriteRecipes = favouriteRecipes
        diffUtilResult.dispatchUpdatesTo(this)
    }
}