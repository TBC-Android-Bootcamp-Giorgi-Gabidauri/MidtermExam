package com.gabo.moviesapp.ui.loggedIn.favourites

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gabo.moviesapp.R
import com.gabo.moviesapp.databinding.FragmentFavouritesBinding
import com.gabo.moviesapp.other.base.BaseFragment

class FavouritesFragment : BaseFragment<FavouritesViewModel,FragmentFavouritesBinding>(
    FavouritesViewModel::class,FragmentFavouritesBinding::inflate
) {
    override fun setupView(savedInstanceState: Bundle?) {

    }

}