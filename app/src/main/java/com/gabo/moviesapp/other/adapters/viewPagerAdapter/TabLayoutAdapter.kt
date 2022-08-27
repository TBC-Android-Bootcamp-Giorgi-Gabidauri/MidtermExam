package com.gabo.moviesapp.other.adapters.viewPagerAdapter

import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.gabo.moviesapp.ui.loggedIn.home.movieDetails.similarMovies.SimilarMoviesFragment
import com.gabo.moviesapp.ui.loggedIn.home.movieDetails.trailers.TrailersFragment


class TabLayoutAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): androidx.fragment.app.Fragment = when (position) {
        0 -> TrailersFragment()
        else -> {
            SimilarMoviesFragment()
        }
    }


}