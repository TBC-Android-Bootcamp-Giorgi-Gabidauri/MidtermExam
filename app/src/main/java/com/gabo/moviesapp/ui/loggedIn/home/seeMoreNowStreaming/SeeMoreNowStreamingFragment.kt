package com.gabo.moviesapp.ui.loggedIn.home.seeMoreNowStreaming

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.gabo.moviesapp.data.models.movieModels.MovieModel
import com.gabo.moviesapp.databinding.FragmentSeeMoreNowStreamingBinding
import com.gabo.moviesapp.other.adapters.loadingAdapter.MoviesLoadingAdapter
import com.gabo.moviesapp.other.adapters.rvAdapters.PopularMoviesAdapter
import com.gabo.moviesapp.other.base.BaseFragment
import com.gabo.moviesapp.other.common.launchStarted
import com.gabo.moviesapp.ui.MainActivity
import kotlinx.android.synthetic.main.loading_item.*

class SeeMoreNowStreamingFragment :
    BaseFragment<SeeMoreNowStreamingViewModel, FragmentSeeMoreNowStreamingBinding>(
        SeeMoreNowStreamingViewModel::class, FragmentSeeMoreNowStreamingBinding::inflate
    ) {
    private lateinit var seeMoreNowStreamingAdapter: PopularMoviesAdapter

    override fun setupView(savedInstanceState: Bundle?) {
        setupAdapters()
        val genresList = (activity as MainActivity).genresList
        seeMoreNowStreamingAdapter.submitList(genresList)
        setupObservers()
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
        findNavController().navigate(
            SeeMoreNowStreamingFragmentDirections.actionSeeMoreNowStreamingFragmentToMovieDetailsFragment(
                model
            )
        )
    }
}