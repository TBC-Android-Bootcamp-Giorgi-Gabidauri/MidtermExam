package com.gabo.moviesapp.ui.loggedIn.profile

import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.gabo.moviesapp.databinding.FragmentProfileBinding
import com.gabo.moviesapp.other.base.BaseFragment
import com.gabo.moviesapp.ui.loggedIn.ViewPagerContainerFragmentDirections
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class ProfileFragment : BaseFragment<ProfileViewModel,FragmentProfileBinding>(
    ProfileViewModel::class,FragmentProfileBinding::inflate
) {
    override fun setupView(savedInstanceState: Bundle?) {
        setUserInfo()
        listeners()
    }

    private fun listeners(){
        binding.ivLogOut.setOnClickListener {
            Firebase.auth.signOut()
            findNavController().navigate(ViewPagerContainerFragmentDirections.actionViewPagerContainerFragmentToLogInFragment())
        }
    }

    private fun setUserInfo() {
        viewLifecycleOwner.lifecycleScope.launch{
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.getUserInfo()
                viewModel.userInfo.collect{
                    if (it != null){
                        binding.tvUsername.text = it.userName
                        binding.tvUserEmail.text = it.email
                    }
                }
            }
        }
    }

}