package com.gabo.moviesapp.ui.loggedIn.home.seeMoreNowStreaming

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.gabo.moviesapp.R
import com.gabo.moviesapp.data.models.movieModels.MovieModel
import com.gabo.moviesapp.databinding.FragmentSeeMoreNowStreamingBinding
import com.gabo.moviesapp.domain.ConnectionLiveData
import com.gabo.moviesapp.other.adapters.loadingAdapter.MoviesLoadingAdapter
import com.gabo.moviesapp.other.adapters.rvAdapters.PopularMoviesAdapter
import com.gabo.moviesapp.other.base.BaseFragment
import com.gabo.moviesapp.other.common.isNetworkAvailable
import com.gabo.moviesapp.other.common.launchStarted
import com.gabo.moviesapp.ui.MainActivity
import com.gabo.moviesapp.ui.MainViewModel
import kotlinx.android.synthetic.main.loading_item.*
import kotlinx.android.synthetic.main.popular_movie_item.*

class SeeMoreNowStreamingFragment :
    BaseFragment<SeeMoreNowStreamingViewModel, FragmentSeeMoreNowStreamingBinding>(
        SeeMoreNowStreamingViewModel::class, FragmentSeeMoreNowStreamingBinding::inflate
    ) {
    private lateinit var connectionLiveData: ConnectionLiveData
    private lateinit var seeMoreNowStreamingAdapter: PopularMoviesAdapter
    private val activityViewModel: MainViewModel by activityViewModels()

    override fun setupView(savedInstanceState: Bundle?) {
        connectionLiveData = ConnectionLiveData(requireContext())
        checkNetwork()
        setupAdapters()
        listeners()
        val genresList = (activity as MainActivity).genresList
        seeMoreNowStreamingAdapter.submitList(genresList)
        if (requireContext().isNetworkAvailable) {
            setupObservers()
        }
    }

    private fun listeners() {
        binding.ivBackButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupAdapters() {
        seeMoreNowStreamingAdapter = PopularMoviesAdapter { navigateToDetails(it) }.also {
            binding.rvSeeMoreNowStreaming.adapter = it.withLoadStateFooter(
                footer = MoviesLoadingAdapter(requireContext()) { it.retry() })
            binding.rvSeeMoreNowStreaming.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun setupObservers() {
        viewLifecycleOwner.launchStarted {
            viewModel.getNowPlayingMovies().collect {
                seeMoreNowStreamingAdapter.submitData(it)
            }
            seeMoreNowStreamingAdapter.loadStateFlow.collect { loadStates ->
                pfRetryState.isVisible = loadStates.refresh !is LoadState.Loading
            }
        }
    }

    private fun navigateToDetails(model: MovieModel) {
        if (requireContext().isNetworkAvailable){
            findNavController().navigate(
                SeeMoreNowStreamingFragmentDirections.actionSeeMoreNowStreamingFragmentToMovieDetailsFragment(
                    model
                )
            )
        }
    }

    private fun checkNetwork() {
        connectionLiveData.observe(this) { isConnected ->
            with(binding) {
                if (isConnected) {
                    tvNoInternet.visibility = View.GONE
                    if (seeMoreNowStreamingAdapter.itemCount > 0) {
                        progressBar.visibility = View.GONE
                    } else {
                        val genresList = (activity as MainActivity).genresList
                        seeMoreNowStreamingAdapter.submitList(genresList)
                        setupObservers()
                    }
                } else {
                    tvNoInternet.visibility = View.VISIBLE
                }
            }
        }
    }
}