package com.seif.foody.ui.fragments.foodjoke

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.seif.foody.R
import com.seif.foody.databinding.FragmentFoodJokeBinding
import com.seif.foody.utils.Constants.Companion.API_KEY
import com.seif.foody.utils.NetworkResult
import com.seif.foody.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FoodJokeFragment : Fragment() {

    private val mainViewModel by viewModels<MainViewModel>()
    private var _binding: FragmentFoodJokeBinding? = null
    private val binding get() = _binding!!

    private var foodJoke = "no food joke"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFoodJokeBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        mainViewModel.getFoodJoke(API_KEY)
        mainViewModel.foodJokeResponse.observe(viewLifecycleOwner) { response ->

            when (response) {
                is NetworkResult.Success -> {
                    binding.txtFoodJoke.text = response.data?.text
                    response.data?.let {
                        foodJoke = it.text
                    }
                }
                is NetworkResult.Error -> {
                    loadDataFromCache()
                    //   binding.txtErrorJoke.text = response.message.toString()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.Loading -> {
                    Log.d("foodJoke", "loading")

                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.food_joke_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_share_food_joke){
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, foodJoke)
            startActivity(shareIntent)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun loadDataFromCache() {
        lifecycleScope.launch {
            mainViewModel.readFoodJoke.observe(viewLifecycleOwner) { database ->
                if (!database.isNullOrEmpty()) {
                    binding.txtFoodJoke.text = database[0].foodJoke.text
                    foodJoke = database[0].foodJoke.text
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}