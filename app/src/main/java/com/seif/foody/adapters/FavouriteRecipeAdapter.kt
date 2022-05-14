package com.seif.foody.adapters

import android.view.*
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.seif.foody.R
import com.seif.foody.data.database.entities.FavouriteEntity
import com.seif.foody.databinding.FavouriteRecipeRowLayoutBinding
import com.seif.foody.ui.fragments.favourites.FavouriteRecipeFragmentDirections
import com.seif.foody.utils.RecipeDiffUtil

class FavouriteRecipeAdapter(
    private val requireActivity: FragmentActivity
) : RecyclerView.Adapter<FavouriteRecipeAdapter.MyViewHolder>(), ActionMode.Callback {

    private var favouriteRecipes = emptyList<FavouriteEntity>()

    class MyViewHolder(val binding: FavouriteRecipeRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(favouriteRecipe: FavouriteEntity) {
            binding.favouriteRecipe = favouriteRecipe
            binding.executePendingBindings() // to update ui when changed
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
        val selectedItem = favouriteRecipes[position]
        holder.bind(selectedItem)

        // click listener
        holder.binding.favouriteRowCardView.setOnClickListener {
            val action =
                FavouriteRecipeFragmentDirections.actionFavouriteRecipeFragmentToDetailsActivity(
                    selectedItem.result
                )
            it.findNavController().navigate(action)
        }

        // long click listener
        holder.binding.favouriteRowCardView.setOnLongClickListener {
            requireActivity.startActionMode(this)
            true
        }
    }

    override fun getItemCount(): Int {
        return favouriteRecipes.size
    }

    fun addFavouriteRecipes(favouriteRecipes: List<FavouriteEntity>) {
        val diffUtilCallBack = RecipeDiffUtil(this.favouriteRecipes, favouriteRecipes)
        val diffUtilResult = DiffUtil.calculateDiff(diffUtilCallBack)
        this.favouriteRecipes = favouriteRecipes
        diffUtilResult.dispatchUpdatesTo(this)
    }

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        mode?.menuInflater?.inflate(R.menu.favourite_contextual_menu, menu)
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        return true
    }

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        return true
    }

    override fun onDestroyActionMode(mode: ActionMode?) {

    }
}