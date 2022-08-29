package com.gabo.moviesapp.ui.loggedIn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.gabo.moviesapp.R
import com.gabo.moviesapp.databinding.FragmentViewPagerContainerBinding
import com.gabo.moviesapp.other.adapters.viewPagerAdapter.ViewPagerAdapter

class ViewPagerContainerFragment : Fragment() {
    private lateinit var binding: FragmentViewPagerContainerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentViewPagerContainerBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBottomBarWithViewPager()
    }

    private fun setupBottomBarWithViewPager() {
        with(binding) {
            viewPager.adapter = ViewPagerAdapter(requireActivity())

            viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    when (position) {
                        0 -> bottomBar.menu.findItem(R.id.homeFragment).isChecked =
                            true
                        1 -> bottomBar.menu.findItem(R.id.searchFragment).isChecked =
                            true
                        else -> bottomBar.menu.findItem(R.id.profileFragment).isChecked =
                            true
                    }
                }
            })

            bottomBar.setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.homeFragment -> {
                        viewPager.currentItem = 0
                        true
                    }
                    R.id.searchFragment -> {
                        viewPager.currentItem = 1
                        true
                    }
                    else -> {
                        viewPager.currentItem = 2
                        true
                    }
                }
            }
        }
    }
}