package com.gabo.moviesapp.ui.loggedIn

import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.gabo.moviesapp.databinding.FragmentLoggedInBinding
import com.gabo.moviesapp.other.base.BaseFragment


class LoggedInFragment : BaseFragment<LoggedInViewModel, FragmentLoggedInBinding>(
    LoggedInViewModel::class,
    FragmentLoggedInBinding::inflate
) {
    override fun setupView(savedInstanceState: Bundle?) {
        val bottomNav = binding.bottomNavView
        val navController = Navigation.findNavController(binding.navHostFragment)
        setupWithNavController(bottomNav,navController)
        Navigation.setViewNavController(bottomNav,navController)
//        bottomNav.setupWithNavController(navController)

//        val navController2 = Navigation.findNavController(this, R.id.activity_main_nav_host_fragment)
//        val bottomNavigationView: BottomNavigationView =
//            findViewById(R.id.activity_main_bottom_navigation_view)
//        setupWithNavController(bottomNavigationView, navController)
    }


}