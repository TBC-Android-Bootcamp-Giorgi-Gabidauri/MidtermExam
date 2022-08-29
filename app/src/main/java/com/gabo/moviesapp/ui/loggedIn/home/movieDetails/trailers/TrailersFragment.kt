package com.gabo.moviesapp.ui.loggedIn.home.movieDetails.trailers

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.gabo.moviesapp.R
import com.gabo.moviesapp.data.models.movieTrailerModels.MovieTrailersModel
import com.gabo.moviesapp.databinding.FragmentTrailersBinding
import com.gabo.moviesapp.domain.ConnectionLiveData
import com.gabo.moviesapp.other.adapters.rvAdapters.TrailersAdapter
import com.gabo.moviesapp.other.base.BaseFragment
import com.gabo.moviesapp.other.common.isNetworkAvailable
import com.gabo.moviesapp.other.common.launchStarted
import com.gabo.moviesapp.other.common.setupAdapter
import com.gabo.moviesapp.other.responseHelpers.ResponseHandler
import com.gabo.moviesapp.ui.MainActivity
import com.gabo.moviesapp.ui.MainViewModel
import com.gabo.moviesapp.ui.loggedIn.home.movieDetails.MovieDetailsFragment
import com.gabo.moviesapp.ui.loggedIn.home.movieDetails.MovieDetailsFragmentArgs
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class TrailersFragment : BaseFragment<TrailersViewModel, FragmentTrailersBinding>(
    TrailersViewModel::class, FragmentTrailersBinding::inflate
) {
    private lateinit var connectionLiveData: ConnectionLiveData
    private val activityViewModel: MainViewModel by activityViewModels()
    private lateinit var trailersAdapter: TrailersAdapter
    override fun setupView(savedInstanceState: Bundle?) {
        connectionLiveData = ConnectionLiveData(requireContext())
        setupAdapters()
        if (requireContext().isNetworkAvailable){
            setupObservers()
        }
        checkNetwork()
    }

    private fun setupAdapters() {
        trailersAdapter =
            binding.rvTrailers.setupAdapter(
                TrailersAdapter(viewLifecycleOwner.lifecycle),
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            )
    }

    private fun setupObservers() {
        viewLifecycleOwner.launchStarted {
            activityViewModel.movieDetailsFragmentArgs.collect {
                it.let { id ->
                    viewModel.getTrailers(id).collect { response ->
                        when (response) {
                            is ResponseHandler.Success<MovieTrailersModel> -> {
                                trailersAdapter.submitList(response.data?.results!!)
                            }
                            else -> {}
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
                    binding.tvNoInternet.visibility = View.GONE
                    if (trailersAdapter.itemCount > 0) {
                        progressBar.visibility = View.GONE
                    } else {
                        setupObservers()
                    }
                } else {
                    binding.tvNoInternet.visibility = View.VISIBLE
                }
            }
        }
    }
}