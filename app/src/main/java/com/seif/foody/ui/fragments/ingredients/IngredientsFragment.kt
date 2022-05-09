package com.seif.foody.ui.fragments.ingredients

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.seif.foody.R
import com.seif.foody.adapters.IngredientAdapter
import com.seif.foody.models.Result
import kotlinx.android.synthetic.main.fragment_ingredients.view.*

class IngredientsFragment : Fragment() {
    private val ingredientAdapter by lazy { IngredientAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_ingredients, container, false)

        val args = arguments
        val myBundle: Result? = args?.getParcelable("recipeBundle")

        ingredientAdapter.addIngredientList(myBundle!!.extendedIngredients)
        view.rv_ingredients.adapter = ingredientAdapter

        return view
    }
}