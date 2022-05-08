package com.seif.foody.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import com.seif.foody.R
import com.seif.foody.adapters.PagerAdapter
import com.seif.foody.databinding.ActivityDetailsBinding
import com.seif.foody.ui.fragments.ingredients.IngredientsFragment
import com.seif.foody.ui.fragments.instructions.InstructionsFragment
import com.seif.foody.ui.fragments.overview.OverviewFragment


class DetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailsBinding
    private val args: DetailsActivityArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        binding.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val fragments = ArrayList<Fragment>()
        fragments.add(OverviewFragment())
        fragments.add(IngredientsFragment())
        fragments.add(InstructionsFragment())

        val titles = ArrayList<String>()
        titles.add("Overview")
        titles.add("Ingredients")
        titles.add("Instructions")

        val resultBundle = Bundle()
        args.result.let {
            resultBundle.putParcelable("recipeBundle", it)
        }

        val adapter = PagerAdapter(
            resultBundle,
            fragments,
            this
        )
        binding.viewPager2.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            tab.text = titles[position]
        }.attach()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}