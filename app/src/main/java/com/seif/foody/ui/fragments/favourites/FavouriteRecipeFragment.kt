package com.seif.foody.ui.fragments.favourites

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.seif.foody.R
import com.seif.foody.adapters.FavouriteRecipeAdapter
import com.seif.foody.databinding.FragmentFavouriteReciepeBinding
import com.seif.foody.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FavouriteRecipeFragment : Fragment() {
    private var _binding: FragmentFavouriteReciepeBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel: MainViewModel by viewModels()
    private val favouriteRecipeAdapter: FavouriteRecipeAdapter by lazy {
        FavouriteRecipeAdapter(
            requireActivity(),
            mainViewModel
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFavouriteReciepeBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel
        binding.myAdapter = favouriteRecipeAdapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        binding.rvFavouriteRecipe.adapter = favouriteRecipeAdapter

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        MenuInflater(requireContext()).inflate(R.menu.favourite_recipes_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.deleteAll_favourite_recipes_menu) {
            mainViewModel.deleteAllFavouriteRecipes()
            showSnackBar()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun showSnackBar() {
        Snackbar.make(
            binding.root,
            "All Recipes Removed",
            Snackbar.LENGTH_SHORT
        ).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        favouriteRecipeAdapter.clearContextualActionMode()
    }
}

// private var _binding : FragmentFavouriteReciepeBinding? = null
// private val binding get() = _binding

// binding.lifecycleOwner = this

//    override fun onDestroy() {
//        super.onDestroy()
//        _binding = null
//    }