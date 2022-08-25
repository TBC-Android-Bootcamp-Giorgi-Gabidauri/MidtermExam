package com.gabo.moviesapp.ui.logIn

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.gabo.moviesapp.other.base.BaseFragment
import com.gabo.moviesapp.databinding.FragmentLogInBinding

class LogInFragment : BaseFragment<LogInViewModel, FragmentLogInBinding>(
    LogInViewModel::class,
    FragmentLogInBinding::inflate
) {
    override fun setupView(savedInstanceState: Bundle?) {
        binding.tv.setOnClickListener {
            findNavController().navigate(LogInFragmentDirections.actionLogInFragmentToLoggedInFragment())
        }
    }

}