package com.gabo.moviesapp.ui.register

import android.os.Bundle
import com.gabo.moviesapp.other.base.BaseFragment
import com.gabo.moviesapp.databinding.FragmentRegisterBinding

class RegisterFragment : BaseFragment<RegisterViewModel, FragmentRegisterBinding>(
    RegisterViewModel::class,
    FragmentRegisterBinding::inflate
) {
    override fun setupView(savedInstanceState: Bundle?) {

    }
}