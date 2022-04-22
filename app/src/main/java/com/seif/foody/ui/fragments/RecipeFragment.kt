package com.seif.foody.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.seif.foody.viewmodels.MainViewModel
import com.seif.foody.R
import com.seif.foody.adapters.RecipesAdapter
import com.seif.foody.utils.NetworkResult
import com.seif.foody.viewmodels.RecipesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_reciepe.*
import kotlinx.android.synthetic.main.fragment_reciepe.view.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipeFragment : Fragment() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var recipesViewModel: RecipesViewModel
    private lateinit var mView: View
    private val myAdapter by lazy { RecipesAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        recipesViewModel = ViewModelProvider(requireActivity())[RecipesViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_reciepe, container, false)
        setupRecyclerView()
       // requestApiData()
        readDatabase()
        return mView
    }
    private fun setupRecyclerView() {
        mView.rv_recipes.layoutManager = LinearLayoutManager(requireContext())
        mView.rv_recipes.adapter = myAdapter
        showShimmerEffectAndHideRecyclerView()
    }

    private fun readDatabase() {
        lifecycleScope.launch {
            mainViewModel.readRecipes.observe(viewLifecycleOwner){ database ->
                if (database.isNotEmpty()){
                    Log.d("RecipesFragment", "read data from database called")
                    myAdapter.setData(database[0].foodRecipe)
                    showRecyclerViewAndHideShimmerEffect()
                }
                else{
                    requestApiData()
                }
            }
        }
    }

    private fun requestApiData(){
        Log.d("RecipesFragment", "request api data called")
        mainViewModel.getRecipes(recipesViewModel.applyQueries())
        mainViewModel.recipesResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    showRecyclerViewAndHideShimmerEffect()
                    response.data?.let { myAdapter.setData(it) }
                }
                is NetworkResult.Error ->{
                    showRecyclerViewAndHideShimmerEffect()
                    loadDataFromCashe()  // load previous data from database when error occurred
                    Toast.makeText(requireContext(), response.message.toString(), Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading ->{
                    showShimmerEffectAndHideRecyclerView()
                }
            }
        }
    }
    private fun loadDataFromCashe(){
        lifecycleScope.launch {
            mainViewModel.readRecipes.observe(viewLifecycleOwner){ database ->
                if (database.isNotEmpty()){
                    myAdapter.setData(database[0].foodRecipe)
                }
            }
        }
    }

    private fun showRecyclerViewAndHideShimmerEffect(){
        mView.shimmerFrameLayout.visibility = View.GONE
        mView.rv_recipes.visibility = View.VISIBLE
    }
    private fun showShimmerEffectAndHideRecyclerView(){
        mView.shimmerFrameLayout.visibility = View.VISIBLE
        mView.rv_recipes.visibility = View.GONE
    }

}