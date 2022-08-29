package com.gabo.moviesapp.ui.loggedIn.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.gabo.moviesapp.data.models.movieModels.MovieModel
import com.gabo.moviesapp.databinding.FragmentProfileBinding
import com.gabo.moviesapp.domain.ConnectionLiveData
import com.gabo.moviesapp.other.adapters.rvAdapters.SimilarMoviesAdapter
import com.gabo.moviesapp.other.base.BaseFragment
import com.gabo.moviesapp.other.common.isNetworkAvailable
import com.gabo.moviesapp.other.common.launchStarted
import com.gabo.moviesapp.other.common.setupAdapter
import com.gabo.moviesapp.ui.MainActivity
import com.gabo.moviesapp.ui.MainViewModel
import com.gabo.moviesapp.ui.loggedIn.ViewPagerContainerFragmentDirections
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class ProfileFragment : BaseFragment<ProfileViewModel, FragmentProfileBinding>(
    ProfileViewModel::class, FragmentProfileBinding::inflate
) {
    private lateinit var connectionLiveData: ConnectionLiveData
    private lateinit var savedItemsAdapter: SimilarMoviesAdapter
    private val activityViewModel: MainViewModel by activityViewModels()

    override fun setupView(savedInstanceState: Bundle?) {
        connectionLiveData = ConnectionLiveData(requireContext())
        checkNetwork()
        if (requireContext().isNetworkAvailable){
            setUserInfo()
        }
        listeners()
        setupAdapters()
    }

    private fun listeners() {
        binding.ivLogOut.setOnClickListener {
            Firebase.auth.signOut()
            findNavController().navigate(ViewPagerContainerFragmentDirections.actionViewPagerContainerFragmentToLogInFragment())
        }
    }

    private fun setUserInfo() {
        viewLifecycleOwner.launchStarted {
            activityViewModel.userInfo.collect {
                if (it != null) {
                    binding.tvUsername.text = it.userName
                    binding.tvUserEmail.text = it.email
                }
            }

        }
    }

    private fun setupAdapters() {
        savedItemsAdapter = binding.rvSavedMovies.setupAdapter(
            SimilarMoviesAdapter { navigateToDetails(it) },
            LinearLayoutManager(requireContext())
        )
        val genresList = (activity as MainActivity).genresList
        savedItemsAdapter.submitGenresList(genresList)
    }

    private fun navigateToDetails(model: MovieModel) {
        if (requireContext().isNetworkAvailable){
            findNavController().navigate(
                ViewPagerContainerFragmentDirections.actionViewPagerContainerFragmentToMovieDetailsFragment(
                    model
                )
            )
        }
    }

    private fun setupObservers() {
        viewModel.getMovies()
        viewLifecycleOwner.launchStarted {
            viewModel.movies.collect {
                savedItemsAdapter.submitList(it)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        setupObservers()
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