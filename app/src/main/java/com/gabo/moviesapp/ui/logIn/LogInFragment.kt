package com.gabo.moviesapp.ui.logIn

import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.gabo.moviesapp.databinding.FragmentLogInBinding
import com.gabo.moviesapp.domain.ConnectionLiveData
import com.gabo.moviesapp.other.base.BaseFragment
import com.gabo.moviesapp.other.common.isNetworkAvailable
import com.gabo.moviesapp.ui.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LogInFragment : BaseFragment<LogInViewModel, FragmentLogInBinding>(
    LogInViewModel::class,
    FragmentLogInBinding::inflate
) {
    private lateinit var connectionLiveData: ConnectionLiveData

    override fun setupView(savedInstanceState: Bundle?) {
        connectionLiveData = ConnectionLiveData(requireContext())
        checkNetwork()
        checkSession()
        listeners()
    }

    private fun checkSession() {
        FirebaseAuth.getInstance().currentUser?.uid?.let {
            findNavController().navigate(LogInFragmentDirections.actionLogInFragmentToViewPagerContainerFragment())
        }
    }

    private fun listeners() {
        binding.btnLogin.setOnClickListener {
            login(
                binding.etEmail.text.toString().trim(),
                binding.etPassword.text.toString().trim()
            )
        }
        binding.signUpLink.setOnClickListener {
            findNavController().navigate(LogInFragmentDirections.actionLogInFragmentToRegisterFragment())
        }
    }

    private fun login(email: String, password: String) {
        if (email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email)
                .matches() && password.isNotEmpty() && password.length > 6
        ) {
            if (requireContext().isNetworkAvailable) {
                binding.progressBar.visibility = View.VISIBLE
                binding.clLogin.visibility = View.GONE
                viewModel.logIn(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(
                            requireContext(),
                            "Logged in successfully",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        findNavController().navigate(LogInFragmentDirections.actionLogInFragmentToViewPagerContainerFragment())
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