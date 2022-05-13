package com.seif.foody.ui.fragments.favourites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.seif.foody.R
import com.seif.foody.adapters.FavouriteRecipeAdapter
import com.seif.foody.databinding.FragmentFavouriteReciepeBinding
import com.seif.foody.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FavouriteRecipeFragment : Fragment() {
    private var _binding: FragmentFavouriteReciepeBinding? = null
    private val binding get() = _binding!!
    private val favouriteRecipeAdapter: FavouriteRecipeAdapter by lazy { FavouriteRecipeAdapter() }
    private val mainViewModel: MainViewModel by viewModels()
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

        binding.rvFavouriteRecipe.adapter = favouriteRecipeAdapter

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}

// private var _binding : FragmentFavouriteReciepeBinding? = null
// private val binding get() = _binding

// binding.lifecycleOwner = this

//    override fun onDestroy() {
//        super.onDestroy()
//        _binding = null
//    }