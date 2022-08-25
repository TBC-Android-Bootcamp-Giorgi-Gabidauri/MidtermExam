package com.gabo.moviesapp.ui.loggedIn.profile

import android.os.Bundle
import com.gabo.moviesapp.databinding.FragmentProfileBinding
import com.gabo.moviesapp.util.base.BaseFragment

class ProfileFragment : BaseFragment<ProfileViewModel,FragmentProfileBinding>(
    ProfileViewModel::class,FragmentProfileBinding::inflate
) {
    override fun setupView(savedInstanceState: Bundle?) {

    }

}