package com.gabo.moviesapp.ui.loggedIn.search

import android.os.Bundle
import com.gabo.moviesapp.databinding.FragmentSearchBinding
import com.gabo.moviesapp.other.base.BaseFragment

class SearchFragment : BaseFragment<SearchViewModel,FragmentSearchBinding>(
    SearchViewModel::class,FragmentSearchBinding::inflate
) {
    override fun setupView(savedInstanceState: Bundle?) {

    }


}