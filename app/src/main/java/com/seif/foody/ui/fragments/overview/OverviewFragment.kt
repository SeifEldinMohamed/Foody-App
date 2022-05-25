package com.seif.foody.ui.fragments.overview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import coil.load
import com.seif.foody.R
import com.seif.foody.databinding.FragmentOverviewBinding
import com.seif.foody.models.Result
import org.jsoup.Jsoup

class OverviewFragment : Fragment() {
    private var _binding: FragmentOverviewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentOverviewBinding.inflate(inflater, container, false)

        val args = arguments
        val myBundle: Result? = args?.getParcelable("recipeBundle")

        binding.mainImageView.load(myBundle?.image)
        binding.txtTitle.text = myBundle?.title
        binding.txtLikes.text = myBundle?.aggregateLikes.toString()
        binding.txtTime.text = myBundle?.readyInMinutes.toString()
        myBundle?.summary.let {
            val summary: String = Jsoup.parse(it!!).text()
            binding.txtSummary.text = summary
        }

        if (myBundle?.vegan == true) {
            binding.veganImage.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
            binding.txtVegan.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }
        if (myBundle?.vegetarian == true) {
            binding.vegetarianImage.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
            binding.txtVegetarian.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
        }
        if (myBundle?.glutenFree == true) {
            binding.glutenFreeImage.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
            binding.txtGlutenFree.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
        }
        if (myBundle?.dairyFree == true) {
            binding.diaryFreeImage.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
            binding.txtDiaryFree.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
        }
        if (myBundle?.cheap == true) {
            binding.cheapImage.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
            binding.txtCheap.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }
        if (myBundle?.veryHealthy == true) {
            binding.healthyImage.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
            binding.txtHealthy.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}