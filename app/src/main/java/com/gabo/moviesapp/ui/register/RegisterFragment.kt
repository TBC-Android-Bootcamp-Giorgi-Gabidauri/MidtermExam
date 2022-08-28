package com.gabo.moviesapp.ui.register

import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.gabo.moviesapp.databinding.FragmentRegisterBinding
import com.gabo.moviesapp.other.base.BaseFragment
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch

val db = FirebaseDatabase.getInstance().getReference("UserData")

class RegisterFragment : BaseFragment<RegisterViewModel, FragmentRegisterBinding>(
    RegisterViewModel::class,
    FragmentRegisterBinding::inflate
) {

    override fun setupView(savedInstanceState: Bundle?) {
        listeners()
    }

    private fun listeners(){

        binding.logInLink.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnRegister.setOnClickListener {
            register(
                binding.etUserName.text.toString().trim(),
                binding.etEmail.text.toString().trim(),
                binding.etPassword.text.toString().trim(),
                binding.etRepeatPassword.text.toString().trim(),
            )
        }
    }

    private fun register(username: String, email: String, password: String, rePassword: String) {
        if (username.isNotEmpty() && email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email)
                .matches() && password.isNotEmpty() && password.length > 6 && password == rePassword
        ) {
            viewLifecycleOwner.lifecycleScope.launch{
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                    viewModel.registerUser(username, email, password)
                    viewModel.registrationState.collect{
                        if (it == "Successful"){
                            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
}