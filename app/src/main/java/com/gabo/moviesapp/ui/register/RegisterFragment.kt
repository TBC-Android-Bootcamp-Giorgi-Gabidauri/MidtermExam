package com.gabo.moviesapp.ui.register

import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.gabo.moviesapp.other.base.BaseFragment
import com.gabo.moviesapp.databinding.FragmentRegisterBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterFragment : BaseFragment<RegisterViewModel, FragmentRegisterBinding>(
    RegisterViewModel::class,
    FragmentRegisterBinding::inflate
) {
    override fun setupView(savedInstanceState: Bundle?) {

        binding.btnRegister.setOnClickListener {
            register(
                binding.etEmail.text.toString().trim(),
                binding.etPassword.text.toString().trim()
            )
        }
    }

    private fun register(email: String, password: String) {
        if (email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email)
                .matches() && password.isNotEmpty() && password.length > 6
        ) {
            Firebase.auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(requireContext(), "Successfully Registered", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(requireContext(), "error!", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}