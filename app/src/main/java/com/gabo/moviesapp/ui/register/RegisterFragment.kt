package com.gabo.moviesapp.ui.register

import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import com.gabo.moviesapp.databinding.FragmentRegisterBinding
import com.gabo.moviesapp.domain.ConnectionLiveData
import com.gabo.moviesapp.other.base.BaseFragment
import com.gabo.moviesapp.other.common.Checkers
import com.gabo.moviesapp.other.common.isNetworkAvailable
import com.gabo.moviesapp.other.common.launchStarted

class RegisterFragment : BaseFragment<RegisterViewModel, FragmentRegisterBinding>(
    RegisterViewModel::class,
    FragmentRegisterBinding::inflate
) {
    private lateinit var connectionLiveData: ConnectionLiveData

    override fun setupView(savedInstanceState: Bundle?) {
        connectionLiveData = ConnectionLiveData(requireContext())
        checkNetwork()
        setupCheckers()
        listeners()
    }

    private fun setupCheckers() {
        val checkers = Checkers(requireContext(), binding)
        var validEmail = false
        var validPassword = false
        var validRepeatedPassword = false
        with(binding) {
            etEmail.doOnTextChanged { _, _, _, _ ->
                validEmail = checkers.emailCheck(etEmail, tilEmailLayout)
            }
            etPassword.doOnTextChanged { _, _, _, _ ->
                validPassword = checkers.emptyError(etPassword, tilPasswordLayout)
            }
            etRepeatPassword.doOnTextChanged { _, _, _, _ ->
                validRepeatedPassword =
                    checkers.repeatPassCheck(etPassword, etRepeatPassword, tilRepeatPassLayout)
            }
            btnRegister.isClickable = validEmail && validPassword && validRepeatedPassword
        }
    }

    private fun listeners() {
        binding.logInLink.setOnClickListener {
            with(binding) {
                if (etEmail.text.toString().isNotEmpty() && etPassword.text.toString()
                        .isNotEmpty() && etUserName.text.toString()
                        .isNotEmpty() && etRepeatPassword.text.toString().isNotEmpty()
                ){
                    findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLogInFragment())
                } else{
                    Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
                }
            }
        }
        binding.btnRegister.setOnClickListener {
            if (requireContext().isNetworkAvailable) {
                register(
                    binding.etUserName.text.toString().trim(),
                    binding.etEmail.text.toString().trim(),
                    binding.etPassword.text.toString().trim(),
                    binding.etRepeatPassword.text.toString().trim(),
                )
            }
        }
    }

    private fun register(username: String, email: String, password: String, repeatedPassword: String) {
        if (requireContext().isNetworkAvailable) {
            viewLifecycleOwner.launchStarted {
                binding.progressBar.visibility = View.VISIBLE
                binding.clRegister.visibility = View.GONE
                viewModel.registerUser(username, email, password)
                viewModel.registrationState.collect {
                    if (it == "Successful") {
                        binding.progressBar.visibility = View.GONE
                        findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToViewPagerContainerFragment())
                        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        binding.progressBar.visibility = View.GONE
                        binding.clRegister.visibility = View.VISIBLE
                        if (it.isNotEmpty()) {
                            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

        }
    }

    private fun checkNetwork() {
        connectionLiveData.observe(this) { isConnected ->
            with(binding) {
                if (isConnected) {
                    tvNoInternet.visibility = View.GONE
                } else {
                    tvNoInternet.visibility = View.VISIBLE
                }
            }
        }
    }
}