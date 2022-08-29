package com.gabo.moviesapp.other.adapters.viewPagerAdapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.gabo.moviesapp.ui.loggedIn.home.HomeFragment
import com.gabo.moviesapp.ui.loggedIn.profile.ProfileFragment
import com.gabo.moviesapp.ui.loggedIn.search.SearchFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> HomeFragment()
        1 -> SearchFragment()
        else -> {
            ProfileFragment()
        }
    }
}