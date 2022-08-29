package com.gabo.moviesapp.ui.register

import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.gabo.moviesapp.databinding.FragmentRegisterBinding
import com.gabo.moviesapp.domain.ConnectionLiveData
import com.gabo.moviesapp.other.base.BaseFragment
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
        listeners()
    }

    private fun listeners() {

        binding.logInLink.setOnClickListener {
            findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLogInFragment())
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

    private fun register(username: String, email: String, password: String, rePassword: String) {
        if (requireContext().isNetworkAvailable) {
            if (username.isNotEmpty() && email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email)
                    .matches() && password.isNotEmpty() && password.length > 6 && password == rePassword
            ) {
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
                            if(it.isNotEmpty()){
                                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                            }
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