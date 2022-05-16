package com.seif.foody.adapters

import android.view.*
import androidx.core.content.ContextCompat
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

    private var multiSelection = false
    private lateinit var mActionMode: ActionMode
    private var selectedRecipes = ArrayList<FavouriteEntity>()
    private val myViewHolders = ArrayList<MyViewHolder>()
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
        val currentRecipe = favouriteRecipes[position]
        holder.bind(currentRecipe)

        myViewHolders.add(holder)

        // click listener
        holder.binding.favouriteRowCardView.setOnClickListener {
            if (multiSelection) {
                appliedSelection(holder, currentRecipe)
            } else { // normal click
                val action =
                    FavouriteRecipeFragmentDirections.actionFavouriteRecipeFragmentToDetailsActivity(
                        currentRecipe.result
                    )
                it.findNavController().navigate(action)
            }
        }

        // long click listener
        holder.binding.favouriteRowCardView.setOnLongClickListener {
            if (!multiSelection) {
                multiSelection = true
                requireActivity.startActionMode(this)
                appliedSelection(holder, currentRecipe)
                true
            } else {
                multiSelection = false
                false
            }

        }
    }

    private fun appliedSelection(holder: MyViewHolder, currentRecipe: FavouriteEntity) {
        if (selectedRecipes.contains(currentRecipe)) { // return to its default style and remove it from selectedRecipesList
            selectedRecipes.remove(currentRecipe)
            changeRecipeStyle(
                holder,
                R.color.backgroundColor,
                R.color.ingredientStrokeColor
            )
            applyActionModeTitle()
        } else {
            selectedRecipes.add(currentRecipe)
            changeRecipeStyle(
                holder,
                R.color.cardBackgroundSelectedColor,
                R.color.colorPrimary
            )
            applyActionModeTitle()
        }
    }

    private fun changeRecipeStyle(holder: MyViewHolder, backgroundColor: Int, strokeColor: Int) {
        holder.binding.favouriteRecipesRowLayout.setBackgroundColor(
            ContextCompat.getColor(
                requireActivity,
                backgroundColor
            )
        )
        holder.binding.favouriteRowCardView.setStrokeColor(
            ContextCompat.getColor(
                requireActivity,
                strokeColor
            )
        )
    }

    private fun applyActionModeTitle(){
        when(selectedRecipes.size){
            0 -> { // empty
                mActionMode.finish()
            }
            1 ->{
                mActionMode.title = "${selectedRecipes.size} item Selected"
            }
            else -> {
                mActionMode.title = "${selectedRecipes.size} items Selected"
            }
        }
    }

    override fun getItemCount(): Int {
        return favouriteRecipes.size
    }


    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        mode?.menuInflater?.inflate(R.menu.favourite_contextual_menu, menu)
        mActionMode = mode!!
        applyStatusBarColor(R.color.contextualStatusBarColor)
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        return true
    }

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        return true
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        myViewHolders.forEach { // for each selected recipe in action mode
            changeRecipeStyle(
                it,
                R.color.cardBackgroundColor,
                R.color.ingredientStrokeColor
            )
        }

        multiSelection = false
        selectedRecipes.clear()
        applyStatusBarColor(R.color.statusBarColor)
    }

    fun applyStatusBarColor(color: Int) {
        requireActivity.window.statusBarColor = ContextCompat.getColor(requireActivity, color)
    }

    fun addFavouriteRecipes(favouriteRecipes: List<FavouriteEntity>) {
        val diffUtilCallBack = RecipeDiffUtil(this.favouriteRecipes, favouriteRecipes)
        val diffUtilResult = DiffUtil.calculateDiff(diffUtilCallBack)
        this.favouriteRecipes = favouriteRecipes
        diffUtilResult.dispatchUpdatesTo(this)
    }


}