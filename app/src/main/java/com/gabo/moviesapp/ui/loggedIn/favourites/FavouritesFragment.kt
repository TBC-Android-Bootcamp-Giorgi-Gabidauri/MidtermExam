package com.gabo.moviesapp.ui.loggedIn.favourites

import android.os.Bundle
import com.gabo.moviesapp.databinding.FragmentFavouritesBinding
import com.gabo.moviesapp.other.base.BaseFragment

class FavouritesFragment : BaseFragment<FavouritesViewModel,FragmentFavouritesBinding>(
    FavouritesViewModel::class,FragmentFavouritesBinding::inflate
) {
    override fun setupView(savedInstanceState: Bundle?) {

    }
}
