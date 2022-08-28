package com.gabo.moviesapp.ui.loggedIn.home.seeMoreNowStreaming

import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.gabo.moviesapp.R
import com.gabo.moviesapp.data.models.movieModels.MovieModel
import com.gabo.moviesapp.databinding.FragmentSeeMoreNowStreamingBinding
import com.gabo.moviesapp.other.adapters.loadingAdapter.MoviesLoadingAdapter
import com.gabo.moviesapp.other.adapters.rvAdapters.PopularMoviesAdapter
import com.gabo.moviesapp.other.base.BaseFragment
import com.gabo.moviesapp.other.common.launchStarted
import com.gabo.moviesapp.ui.MainActivity
import com.gabo.moviesapp.ui.MainViewModel
import kotlinx.android.synthetic.main.loading_item.*
import kotlinx.android.synthetic.main.popular_movie_item.*

class SeeMoreNowStreamingFragment :
    BaseFragment<SeeMoreNowStreamingViewModel, FragmentSeeMoreNowStreamingBinding>(
        SeeMoreNowStreamingViewModel::class, FragmentSeeMoreNowStreamingBinding::inflate
    ) {
    private lateinit var seeMoreNowStreamingAdapter: PopularMoviesAdapter
    private val activityViewModel: MainViewModel by activityViewModels()

    override fun setupView(savedInstanceState: Bundle?) {
        setupAdapters()
        listeners()
        val genresList = (activity as MainActivity).genresList
        seeMoreNowStreamingAdapter.submitList(genresList)
        setupObservers()
    }

    private fun listeners() {
        binding.ivBackButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupAdapters() {
        seeMoreNowStreamingAdapter = PopularMoviesAdapter(
            itemClick = { navigateToDetails(it) },
            saveClick = { movieModel,i -> saveStateControl(movieModel)
                seeMoreNowStreamingAdapter.notifyItemChanged(i)}
        ).also {
            binding.rvSeeMoreNowStreaming.adapter = it.withLoadStateFooter(
                footer = MoviesLoadingAdapter(requireContext()) { it.retry() })
            binding.rvSeeMoreNowStreaming.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun saveStateControl(movieModel: MovieModel) {
        if (movieModel.isSaved == true) {
            movieModel.isSaved = false
            activityViewModel.deleteMovie(movieModel.id)
            ivSaveMovie.setImageResource(R.drawable.ic_save_item)
            Toast.makeText(requireContext(), "Removed", Toast.LENGTH_SHORT).show()
        } else {
            movieModel.isSaved = true
            activityViewModel.saveMovie(movieModel)
            ivSaveMovie.setImageResource(R.drawable.ic_save_item_filled)
            Toast.makeText(requireContext(), "Saved", Toast.LENGTH_SHORT).show()
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