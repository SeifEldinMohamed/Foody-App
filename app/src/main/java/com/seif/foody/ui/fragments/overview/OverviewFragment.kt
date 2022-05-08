package com.seif.foody.ui.fragments.overview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import coil.load
import com.seif.foody.R
import com.seif.foody.models.Result
import kotlinx.android.synthetic.main.fragment_overview.view.*

class OverviewFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_overview, container, false)

        val args = arguments
        val myBundle: Result? = args?.getParcelable("recipeBundle")

        view.main_imageView.load(myBundle?.image)
        view.txt_title.text = myBundle?.title
        view.txt_likes.text = myBundle?.aggregateLikes.toString()
        view.txt_time.text = myBundle?.readyInMinutes.toString()
        view.txt_summary.text = myBundle?.summary

        if (myBundle?.vegan == true){
            view.vegan_image.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            view.txt_vegan.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }
        if (myBundle?.vegetarian == true){
            view.vegetarian_image.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            view.txt_vegetarian.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }
        if (myBundle?.glutenFree == true){
            view.gluten_free_image.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            view.txt_gluten_free.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }
        if (myBundle?.dairyFree == true){
            view.diary_free_image.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            view.txt_diary_free.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }
        if (myBundle?.cheap == true){
            view.cheap_image.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            view.txt_cheap.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }
        if (myBundle?.veryHealthy == true){
            view.healthy_image.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            view.txt_healthy.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }

        return view
    }
}