package com.seif.foody.ui.fragments.recipes

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.seif.foody.R
import com.seif.foody.viewmodels.MainViewModel
import com.seif.foody.adapters.RecipesAdapter
import com.seif.foody.databinding.FragmentReciepeBinding
import com.seif.foody.utils.NetworkListener
import com.seif.foody.utils.NetworkResult
import com.seif.foody.utils.observeOnce
import com.seif.foody.viewmodels.RecipesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipeFragment : Fragment(), SearchView.OnQueryTextListener {
    private var _binding: FragmentReciepeBinding? = null
    private val binding get() = _binding!!
    private lateinit var mainViewModel: MainViewModel
    private lateinit var recipesViewModel: RecipesViewModel
    private val myAdapter by lazy { RecipesAdapter() }
    private val args by navArgs<RecipeFragmentArgs>()
    private lateinit var networkListener: NetworkListener

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
        _binding = FragmentReciepeBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this // bec we will use live data objects in our xml
        binding.mainViewModel = mainViewModel
        setupRecyclerView()
        // requestApiData()

        setHasOptionsMenu(true)

        recipesViewModel.readBackOnline.observe(viewLifecycleOwner){
            recipesViewModel.backOnline = it
        }

        lifecycleScope.launchWhenStarted {
            networkListener = NetworkListener()
            networkListener.checkNetworkAvailability(requireContext())
                .collect { status ->
                    Log.d("NetworkListener", status.toString())
                    recipesViewModel.networkStatus = status
                    recipesViewModel.showNetworkStatus()
                    readDatabase()
                }
        }

        binding.recipesFab.setOnClickListener {
            if (recipesViewModel.networkStatus) {
                findNavController().navigate(R.id.action_recipeFragment_to_recipesBottomSheet)
            } else { // no internet connection
                recipesViewModel.showNetworkStatus()
            }
        }
        return binding.root
    }

    private fun setupRecyclerView() {
        binding.rvRecipes.layoutManager = LinearLayoutManager(requireContext())
        binding.rvRecipes.adapter = myAdapter
        showShimmerEffectAndHideRecyclerView()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.recipes_menu, menu)

        val search = menu.findItem(R.id.menu_search)
        val searchView = search.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)

    }
    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

    private fun readDatabase() {
        lifecycleScope.launch {
            mainViewModel.readRecipes.observeOnce(viewLifecycleOwner) { database ->
                if (database.isNotEmpty() && args.backFromBottomSheet == "false") {
                    Log.d("RecipesFragment", "read data from database called")
                    myAdapter.setData(database[0].foodRecipe)
                    showRecyclerViewAndHideShimmerEffect()
                } else {
                    requestApiData()
                }
            }
        }
    }

    private fun requestApiData() {
        Log.d("RecipesFragment", "request api data called")
        mainViewModel.getRecipes(recipesViewModel.applyQueries())
        mainViewModel.recipesResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    showRecyclerViewAndHideShimmerEffect()
                    response.data?.let { myAdapter.setData(it) }
                }
                is NetworkResult.Error -> {
                    showRecyclerViewAndHideShimmerEffect()
                    loadDataFromCashe()  // load previous data from database when error occurred
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.Loading -> {
                    showShimmerEffectAndHideRecyclerView()
                }
            }
        }
    }

    private fun loadDataFromCashe() {
        lifecycleScope.launch {
            mainViewModel.readRecipes.observe(viewLifecycleOwner) { database ->
                if (database.isNotEmpty()) {
                    myAdapter.setData(database[0].foodRecipe)
                }
            }
        }
    }

    private fun showRecyclerViewAndHideShimmerEffect() {
        binding.shimmerFrameLayout.visibility = View.GONE
        binding.rvRecipes.visibility = View.VISIBLE
    }

    private fun showShimmerEffectAndHideRecyclerView() {
        binding.shimmerFrameLayout.visibility = View.VISIBLE
        binding.rvRecipes.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null // to avoid memory leaks
    }


}