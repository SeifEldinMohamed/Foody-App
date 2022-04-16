package com.seif.foody.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.seif.foody.MainViewModel
import com.seif.foody.R
import com.seif.foody.adapters.RecipesAdapter
import com.seif.foody.utils.Constants
import com.seif.foody.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_reciepe.view.*

@AndroidEntryPoint
class RecipeFragment : Fragment() {
    private lateinit var viewModel:MainViewModel
    private lateinit var mView: View
    private val myAdapter by lazy { RecipesAdapter() }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_reciepe, container, false)
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        setupRecyclerView()
        requestApiData()
        return mView
    }
    private fun requestApiData(){
        viewModel.getRecipes(applyQueries())
        viewModel.recipesResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    showRecyclerViewAndHideShimmerEffect()
                    response.data?.let { myAdapter.setData(it) }
                }
                is NetworkResult.Error ->{
                    showRecyclerViewAndHideShimmerEffect()
                    Toast.makeText(requireContext(), response.message.toString(), Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading ->{
                    showShimmerEffectAndHideRecyclerView()
                }
            }
        }
    }
    private fun applyQueries():HashMap<String, String>{
        val queries : HashMap<String, String> = HashMap()
        queries["number"] = "50" // number of recipes to get from our request [1..100]
        queries["apiKey"] = Constants.API_KEY
        queries["type"] = "snack" // we will get value dynamically
        queries["diet"] = "vegan"
        queries["addRecipeInformation"] = "true"
        queries["fillIngredients"] = "true"
        return queries
    }

    private fun setupRecyclerView() {
        mView.rv_recipes.layoutManager = LinearLayoutManager(requireContext())
        mView.rv_recipes.adapter = myAdapter
        showShimmerEffectAndHideRecyclerView()
    }
    fun showRecyclerViewAndHideShimmerEffect(){
        mView.shimmerFrameLayout.visibility = View.GONE
        mView.rv_recipes.visibility = View.VISIBLE
    }
    fun showShimmerEffectAndHideRecyclerView(){
        mView.shimmerFrameLayout.visibility = View.VISIBLE
        mView.rv_recipes.visibility = View.GONE
    }


}