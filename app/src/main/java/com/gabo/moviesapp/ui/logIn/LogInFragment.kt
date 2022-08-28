package com.gabo.moviesapp.ui.logIn

import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.gabo.moviesapp.databinding.FragmentLogInBinding
import com.gabo.moviesapp.other.base.BaseFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LogInFragment : BaseFragment<LogInViewModel, FragmentLogInBinding>(
    LogInViewModel::class,
    FragmentLogInBinding::inflate
) {

    override fun setupView(savedInstanceState: Bundle?) {
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
            Firebase.auth.signInWithEmailAndPassword(
                email,
                password
            ).addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(requireContext(), "Logged in successfully", Toast.LENGTH_SHORT)
                        .show()
                    findNavController().navigate(LogInFragmentDirections.actionLogInFragmentToViewPagerContainerFragment())
                }
            }
        }
    }
}