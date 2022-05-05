package com.seif.foody.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class PagerAdapter(
   private val resultBundle: Bundle, // used to pass result from details activity to our fragments
   private val fragments: ArrayList<Fragment>,
   private val title: ArrayList<String>,
   fm: FragmentManager
): FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getCount(): Int {
        return fragments.size // 3
    }

    override fun getItem(position: Int): Fragment {
        fragments[position].arguments = resultBundle
        return fragments[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
       return title[position]
    }
}

/** BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT **/
// Indicates that only the current fragment will be in the Lifecycle.State.RESUMED state.
// All other Fragments are capped at Lifecycle.State.STARTED.