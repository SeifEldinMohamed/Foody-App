package com.seif.foody.ui.fragments.recipes.bottomsheet

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.seif.foody.R
import com.seif.foody.utils.Constants.Companion.DEFAULT_DIET_TYPE
import com.seif.foody.utils.Constants.Companion.DEFAULT_MEAL_TYPE
import com.seif.foody.viewmodels.RecipesViewModel
import kotlinx.android.synthetic.main.recipes_bottom_sheet.view.*
import java.util.*

class RecipesBottomSheet : BottomSheetDialogFragment() {
    private lateinit var recipesViewModel: RecipesViewModel

    private var mealTypeChip = DEFAULT_MEAL_TYPE
    private var mealTypeChipId = 0
    private var dietTypeChip = DEFAULT_DIET_TYPE
    private var dietTypeChipId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recipesViewModel = ViewModelProvider(requireActivity())[RecipesViewModel::class.java]


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.recipes_bottom_sheet, container, false)

        recipesViewModel.readMealAndDietType.asLiveData().observe(viewLifecycleOwner){ value ->
            mealTypeChip = value.selectedMealType
            dietTypeChip = value.selectedDietType
            updateChip(value.selectedMealTypeId, view.mealType_chipGroup)
            updateChip(value.selectedDietTypeId, view.diet_chipGroup)
        }

        view.mealType_chipGroup.setOnCheckedChangeListener { group, selectedChipId ->
            val chip = group.findViewById<Chip>(selectedChipId)
            val selectedMealTyp = chip.text.toString().lowercase(Locale.getDefault())
            mealTypeChip = selectedMealTyp
            mealTypeChipId = selectedChipId
            Log.d("bottomSheet", "$mealTypeChipId / $mealTypeChip")
        }

        view.diet_chipGroup.setOnCheckedChangeListener{ group, selectedChipId ->
            val chip = group.findViewById<Chip>(selectedChipId)
            val selectedDietType = chip.text.toString().lowercase(Locale.getDefault())
            dietTypeChip = selectedDietType
            dietTypeChipId = selectedChipId
            Log.d("bottomSheet", "$dietTypeChipId / $dietTypeChip")

        }
        view.btn_apply.setOnClickListener {
            recipesViewModel.saveMealTypeAndType(mealTypeChip, mealTypeChipId, dietTypeChip, dietTypeChipId)

        }

        return view
    }

    private fun updateChip(chipId: Int, chipGroup: ChipGroup) {
        if(chipId !=0){
            try {
                chipGroup.findViewById<Chip>(chipId).isChecked = true
            }
            catch (e:Exception){
                Log.d("bottomSheet", e.message.toString())
            }
        }
    }

}