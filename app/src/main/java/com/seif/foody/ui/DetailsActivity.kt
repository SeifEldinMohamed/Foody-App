package com.seif.foody.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.seif.foody.R
import com.seif.foody.adapters.PagerAdapter
import com.seif.foody.data.database.entities.FavouriteEntity
import com.seif.foody.databinding.ActivityDetailsBinding
import com.seif.foody.ui.fragments.ingredients.IngredientsFragment
import com.seif.foody.ui.fragments.instructions.InstructionsFragment
import com.seif.foody.ui.fragments.overview.OverviewFragment
import com.seif.foody.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailsBinding
    private val args: DetailsActivityArgs by navArgs()
    private val mainViewModel: MainViewModel by viewModels()

    private var recipeSaved: Boolean = false
    private var recipeSavedId: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        binding.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val fragments = ArrayList<Fragment>()
        fragments.add(OverviewFragment())
        fragments.add(IngredientsFragment())
        fragments.add(InstructionsFragment())

        val titles = ArrayList<String>()
        titles.add("Overview")
        titles.add("Ingredients")
        titles.add("Instructions")

        val resultBundle = Bundle()
        args.result.let {
            resultBundle.putParcelable("recipeBundle", it)
        }

        val adapter = PagerAdapter(
            resultBundle,
            fragments,
            this
        )
        binding.viewPager2.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            tab.text = titles[position]
        }.attach()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_menu, menu)
        val menuItem = menu?.findItem(R.id.save_to_favourites_menu)
        checkSavedRecipes(menuItem!!)
        return true
    }

    private fun checkSavedRecipes(menuItem: MenuItem) {
        mainViewModel.readFavouriteRecipes.observe(this) { favouriteRecipes ->
            try {
                for (favRecipe in favouriteRecipes) {
                    if (favRecipe.result.id == args.result.id) { // true when entered recipes is saved in our favourites
                        changeMenuIconColor(menuItem, R.color.yellow)
                        recipeSavedId = favRecipe.id
                        recipeSaved = true
                    } else {
                        changeMenuIconColor(menuItem, R.color.white)
                    }
                }
            } catch (e: Exception) {
                Log.d("details Activity", e.message.toString())
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        } else if (item.itemId == R.id.save_to_favourites_menu && !recipeSaved) {
            saveToFavourites(item)
        } else if (item.itemId == R.id.save_to_favourites_menu && recipeSaved) {
            removeRecipeFromFavourite(item)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveToFavourites(item: MenuItem) {
        val favouriteRecipe = FavouriteEntity(
            0,
            args.result
        )
        mainViewModel.insertFavouriteRecipe(favouriteRecipe)
        changeMenuIconColor(item, R.color.yellow)
        showSnackBar("Saved Successfully")
        recipeSaved = true
    }

    private fun removeRecipeFromFavourite(item: MenuItem) {
        val favouriteRecipe = FavouriteEntity(
            recipeSavedId,
            args.result
        )
        mainViewModel.deleteFavouriteRecipe(favouriteRecipe)
        changeMenuIconColor(item, R.color.white)
        showSnackBar("Removed from favourites")
        recipeSaved = false
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(
            binding.detailsLayout,
            message,
            Snackbar.LENGTH_SHORT
        ).setAction("Okay") {}
            .show()
    }

    private fun changeMenuIconColor(item: MenuItem, color: Int) {
        item.icon.setTint(ContextCompat.getColor(this, color))
    }
}